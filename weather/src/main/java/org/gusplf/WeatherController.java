package org.gusplf;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.net.ConnectException;
import java.util.Random;
import java.util.logging.Logger;

@Path("/weather")
public class WeatherController {

    Logger LOGGER = Logger.getLogger("Demologger");

    @GET
    @Path("/today")
    @Produces(MediaType.TEXT_PLAIN)
    public String todayGet() {
        Random random = new Random();

        int i = random.nextInt(0, 100);

        if (i > 0 && i < 50) {
            LOGGER.info("Error...");
            throw new RuntimeException("A error has ocorred");
        }

        if (i % 2 == 0) {
            return "Sunny";
        }

        return "Cloudy";
    }

    @GET
    @Path("/error")
    @Produces(MediaType.TEXT_PLAIN)
    public String weatherErrorGet() {

        LOGGER.info("Executing...");
        Random random = new Random();

        int i = random.nextInt(0, 100);

        if (i > 10 && i < 70) {
            LOGGER.info("Error...");
            throw new RuntimeException("A error has ocorred");
        }

        if (i % 2 == 0) {
            return "Sunny";
        }

        return "Cloudy";
    }
}
