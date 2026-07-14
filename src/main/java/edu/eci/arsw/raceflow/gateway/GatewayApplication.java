package edu.eci.arsw.raceflow.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the API Gateway. Routes incoming requests to auth-service and
 * realtime-service, and handles cross-cutting concerns like CORS.
 */
@SpringBootApplication
public class GatewayApplication {
    /** @param args command-line arguments passed to Spring Boot */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
