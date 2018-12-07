/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dto.dataset.DataEntry;
import dto.dataset.Dataset;
import dto.jpdi.DescriptorRequest;
import ij.ImagePlus;
import image.ApplicationMain;
import image.models.Measurement;
import image.models.Result;
import org.apache.commons.codec.binary.Base64;
import web.SphericalController;

import javax.imageio.ImageIO;
import javax.inject.Inject;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hampos
 */
@Path("spherical")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnalysisResource {
    private static final Logger LOG = Logger.getLogger(AnalysisResource.class.getName());

    @Inject
    SphericalController sphericalController;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("calculate")
    public Response calculate(DescriptorRequest descriptorRequest) {

try{
        Map<String, Object> parameters = descriptorRequest.getParameters() != null ? descriptorRequest.getParameters() : new HashMap<>();
        String filter = (String) parameters.getOrDefault("filter", "Default");
        String type = (String) parameters.getOrDefault("type", "Default");

        Dataset responseDataset = new Dataset();
        //for (DataEntry dataEntry :descriptorRequest.getDataset().getDataEntry()){
        DataEntry dataEntry = descriptorRequest.getDataset().getDataEntry().get(0);
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

        Measurement measurementModel = new Measurement();
        List<String> selectedMeasurements = measurementModel.getMeasurementList();

        ApplicationMain applicationMain = new ApplicationMain(
                selectedMeasurements.toArray(new String[selectedMeasurements.size()]), filter, imagePlus);

        Result result = null;
        if (type == null || type.isEmpty() || type.equals("Analyze")) {
            result = applicationMain.analyseImage();
        } else if (type.equals("Count")) {
            result = applicationMain.countParticles();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("bad type provided").build();
        }

        System.out.println("Number of ParticleResults:" + result.getParticleResults().size());
        return Response.ok(result.getParticleResults()).build();
    } catch (MalformedURLException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("bad uri provided").build();
        } catch (IOException ex) {
            Logger.getLogger(AnalysisResource.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
