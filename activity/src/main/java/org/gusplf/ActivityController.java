package org.gusplf;

import io.smallrye.faulttolerance.api.RateLimit;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.gusplf.connection.WeatherClient;

import java.time.temporal.ChronoUnit;

@Path("/activity")
public class ActivityController {

    @Inject
    @RestClient
    WeatherClient weatherClient;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Retry(maxDuration = 5000, maxRetries = 10, delay = 500)
    @RateLimit(value = 10, window = 1, windowUnit = ChronoUnit.MINUTES)
    public String hello() {
        if (weatherClient.todayGet().equals("Sunny")) {
            return "Today is a good day to go outside and run";
        } else {
            return "Today isn't a good day to go outside";
        }
    }

    @GET
    @Path("/error")
    @Produces(MediaType.TEXT_PLAIN)
    @CircuitBreaker(requestVolumeThreshold = 5, failureRatio = 0.5,
            delay = 5000)
//    @Retry(maxDuration = 5000, maxRetries = 100, delay = 500) --> Simulação onde ele pode tentar varias vezes
    public String error() {
        if (weatherClient.weatherErrorGet().equals("Sunny")) {
            return "Today is a good day to go outside and run";
        } else {
            return "Today isn't a good day to go outside";
        }
    }
}
