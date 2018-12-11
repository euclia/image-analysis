package resources;

import dto.jpdi.DescriptorRequest;
import dto.jpdi.DescriptorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        return Response.ok(new DescriptorResponse()).build();

    }
}
