package org.gusplf.connection;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Duration;

@ApplicationScoped
public class WeatherConnectionService {

    private final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofMillis(5000))
            .permittedNumberOfCallsInHalfOpenState(2)
            .slidingWindowSize(5)
            .build();
    private final CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(config);
    @Inject
    @RestClient
    WeatherClient weatherClient;

    public String getTodayWeather(String terminalId) {
        String result;
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("weatherApiCircuitBreaker_" + terminalId);

        try {
            result = circuitBreaker.executeSupplier(this::callWeatherAPI);
        } catch (CallNotPermittedException e) {
            result = "Circuit Breaker is open";
        } catch (RuntimeException e) {
            result = "Weather is unavailable";
        }

        return result;
    }

    private String callWeatherAPI() {
        return weatherClient.todayGet();
    }
}
