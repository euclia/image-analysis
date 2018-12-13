package resources;

import dto.dataset.DataEntry;
import dto.dataset.Dataset;
import dto.dataset.FeatureInfo;
import dto.jpdi.DescriptorRequest;
import dto.jpdi.DescriptorResponse;
import ij.ImagePlus;
import image.ApplicationMain;
import image.models.spherical.SphericalOptions;
import image.models.spherical.SphericalReport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("nanotubes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class NanotubesResource {
    private static final Logger LOG = Logger.getLogger(resources.NanotubesResource.class.getName());

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("calculate")
    @Operation(summary = "Calculate nanotubes descriptors",
            tags = {"nanotubes"},
            description = "Returns a DescriptorResponse containing the derived nanotubes descriptors from input images",
            responses = {
                    @ApiResponse(description = "Descriptor Response", content = @Content(
                            schema = @Schema(implementation = DescriptorResponse.class)
                    ))
            })
    public Response calculate(@Parameter(description = "Descriptor Request") DescriptorRequest descriptorRequest) {

        try {
            Map<String, Object> parameters = descriptorRequest.getParameters() != null ? descriptorRequest.getParameters() : new HashMap<>();
            String filter = (String) parameters.getOrDefault("filter", "Default");

            Dataset responseDataset = new Dataset();
            Set<FeatureInfo> featureInfoList = new HashSet<>();
            LinkedList<DataEntry> dataEntryList = new LinkedList<>();
            int entryId = 0;
            for (DataEntry dataEntry : descriptorRequest.getDataset().getDataEntry()) {

                BufferedImage bufferedImage = null;

                String imageEncoded = "";
                Double scale = 1d;

                if (dataEntry.getValues().size() > 2)
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid size of data Entry").build();

                for (Object value : dataEntry.getValues().values()) {
                    if (String.valueOf(value).startsWith("data:image"))
                        imageEncoded = String.valueOf(value);
                    else
                        scale = Double.valueOf(value.toString());
                }

                String base64Image = imageEncoded.split(",")[1];
                byte[] decoded = Base64.decodeBase64(base64Image.getBytes());
                bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded));

                ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);


                SphericalOptions sphericalOptionsModel = new SphericalOptions();
                List<String> selectedMeasurements = sphericalOptionsModel.getMeasurementList();

                ApplicationMain applicationMain = new ApplicationMain(selectedMeasurements.toArray(new String[selectedMeasurements.size()]), filter, imagePlus);
                SphericalReport sphericalReport = null;

                sphericalReport = applicationMain.countParticles();

                // datasetMakerHelper.getEntryList(DatasetMakerHelper.Particle.SPHERICAL, entryId++, dataEntryList, sphericalReport.getStaticParticle().getParticleResult());
            }
            //datasetMakerHelper.getFeatureList(DatasetMakerHelper.Particle.SPHERICAL, featureInfoList, dataEntryList.getFirst());

            responseDataset.setDataEntry(dataEntryList);
            responseDataset.setFeatures(featureInfoList);

            DescriptorResponse descriptorResponse = new DescriptorResponse();
            descriptorResponse.setResponseDataset(responseDataset);


            return Response.ok(descriptorResponse).build();

        } catch (
                MalformedURLException ex) {
            Logger.getLogger(SphericalResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("bad uri provided").build();
        } catch (
                IOException ex) {
            Logger.getLogger(SphericalResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
