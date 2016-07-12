/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import ij.ImagePlus;
import image.ApplicationMain;
import image.models.Measurement;
import image.models.Result;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.codec.binary.Base64;
import web.IndexController;

/**
 *
 * @author hampos
 */
@Path("analyze")
@Named
public class AnalysisResource {

    @Inject
    IndexController indexController;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response analyze(@FormParam("image") String image,
            @FormParam("filter") String filter,
            @FormParam("type") String type) {
        try {
            if (filter == null || filter.isEmpty()) {
                filter = "Default";
            }
            System.out.println(image);
            String fileName = UUID.randomUUID().toString();
            java.nio.file.Path path = FileSystems.getDefault().getPath("tmp", fileName);
            if (!path.toFile().getParentFile().exists()) {
                path.toFile().mkdirs();
            }
            path.toFile().createNewFile();

            BufferedImage bufferedImage = null;
            if (image.startsWith("data:image")) {
                String base64Image = image.split(",")[1];
                byte[] decoded = Base64.decodeBase64(base64Image.getBytes());
                bufferedImage = ImageIO.read(new ByteArrayInputStream(decoded));
            } else {
                URL website = new URL(image);
                Files.copy(website.openStream(), path, StandardCopyOption.REPLACE_EXISTING);
                bufferedImage = ImageIO.read(new FileInputStream(path.toFile()));
            }

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

            Logger.getLogger(AnalysisResource.class.getName()).log(Level.INFO, "Deleting tmp file:{0}", fileName);
            path.toFile().delete();
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
