package edu.eci.arsw.raceflow.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada del API Gateway. Enruta las peticiones entrantes hacia auth-service y
 * realtime-service, y maneja preocupaciones transversales como CORS.
 */
@SpringBootApplication
public class GatewayApplication {
    /** @param args argumentos de línea de comandos pasados a Spring Boot */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
