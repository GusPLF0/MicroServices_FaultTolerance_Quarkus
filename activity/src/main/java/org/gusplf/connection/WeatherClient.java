package org.gusplf.connection;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8085/weather")
public interface WeatherClient {

    @GET
    @Path("/today")
    @Produces(MediaType.TEXT_PLAIN)
    String todayGet();

    @GET
    @Path("/error")
    @Produces(MediaType.TEXT_PLAIN)
    String weatherErrorGet();

}
