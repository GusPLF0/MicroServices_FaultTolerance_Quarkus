package org.gusplf;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.gusplf.connection.WeatherClient;
import org.gusplf.connection.WeatherConnectionService;

@Path("/activity")
public class ActivityController {

    @Inject
    @RestClient
    WeatherClient weatherClient;

    @Inject
    WeatherConnectionService weatherConnectionService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
//    @Retry(maxDuration = 5000, maxRetries = 10, delay = 500)
//    @RateLimit(value = 10, window = 1, windowUnit = ChronoUnit.MINUTES)
    public String hello(@HeaderParam("terminalId") String terminalId) {
        String todayWeather = weatherConnectionService.getTodayWeather(terminalId);
        if (todayWeather.equals("Sunny")) {
            return "Today is a good day to go outside and run " + terminalId;
        } else if (todayWeather.equals("Cloudy")) {
            return "Today isn't a good day to go outside " + terminalId;
        }
        return todayWeather + " " + terminalId;
    }

    @GET
    @Path("/error")
    @Produces(MediaType.TEXT_PLAIN)
//    @Retry(maxDuration = 5000, maxRetries = 100, delay = 500) --> Simulação onde ele pode tentar varias vezes
    public String error() {
        if (weatherClient.weatherErrorGet().equals("Sunny")) {
            return "Today is a good day to go outside and run";
        } else {
            return "Today isn't a good day to go outside";
        }
    }

}
