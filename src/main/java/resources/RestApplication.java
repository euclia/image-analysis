/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import com.google.common.collect.Lists;
import image.helpers.DatasetMakerHelper;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.jaxrs2.integration.resources.AcceptHeaderOpenApiResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.servlet.ServletConfig;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationPath("/descriptors")
public class RestApplication extends Application {

    public RestApplication(@Context ServletConfig servletConfig) {
        super();
        OpenAPI oas = new OpenAPI();
        Info info = new Info()
                .title("NanoImage API")
                .description("NanoImage, part of the Jaqpot Platform, offers tools for analysis of electron microscopy " +
                        "images, allowing the user to derive descriptors for the materials directly from the images, " +
                        "offering distinct advantage over manual procedures, in terms of speed and ability to represent " +
                        "the whole sample. It is not up to the microscope operator to capture measurements that express " +
                        "the frequency of occurrence of materials with certain dimensions/shapes or the presence of " +
                        "materials with outlier dimensions/shapes.")
                .contact(new Contact()
                        .email("ang.valsamis@gmail.com"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        oas.info(info);
        Server server = new Server();
        server.setUrl("/nanoImage");
        List<Server> serverList = new ArrayList<>();
        serverList.add(server);
        oas.setServers(serverList);

        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of("main.java.resources").collect(Collectors.toSet()));
        try {
            new JaxrsOpenApiContextBuilder()
                    .servletConfig(servletConfig)
                    .application(this)
                    .openApiConfiguration(oasConfig)
                    .buildContext(true);
        } catch (OpenApiConfigurationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();
        resources.add(SphericalResource.class);
        resources.add(NanotubesResource.class);
        resources.add(DatasetMakerHelper.class);
        resources.add(OpenApiResource.class);
        resources.add(AcceptHeaderOpenApiResource.class);
        return resources;
    }
}
