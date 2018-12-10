package resources;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@Path("nanotubes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
public class NanotubesResource {
    private static final Logger LOG = Logger.getLogger(resources.SphericalResource.class.getName());

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("calculate")
    @Operation(summary = "Calculate nanotubes descriptors",
            tags = {"spherical"},
            description = "Returns a DescriptorResponse containing the derived nanotubes descriptors from input images",
            responses = {
                    @ApiResponse(description = "Descriptor Response", content = @Content(
                            schema = @Schema(implementation = String.class)
                    ))
            })
    public String calculate() {
        return "bla";

    }
}
