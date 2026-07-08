package edu.eci.arsw.raceflow.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Verifies the SecurityConfig permitAll chain is actually wired -- without it,
 * spring-boot-starter-security would challenge every request with a generated
 * Basic Auth password and every route would 401 instead of reaching the gateway
 * predicates at all.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class GatewayRoutingTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void unmappedPathReturnsNotFoundInsteadOfUnauthorized() {
        webTestClient.get().uri("/does-not-exist")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void actuatorHealthIsReachableWithoutAuthentication() {
        webTestClient.get().uri("/actuator/health")
                .exchange()
                .expectStatus().isOk();
    }
}
