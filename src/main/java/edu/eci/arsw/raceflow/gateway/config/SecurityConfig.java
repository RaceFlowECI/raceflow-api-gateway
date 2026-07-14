package edu.eci.arsw.raceflow.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/** Configuración de seguridad WebFlux para el pipeline reactivo de peticiones del gateway. */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * El propio gateway no autentica las peticiones -- cada ruta proxeada llega a un
     * servicio downstream (auth-service, realtime-service) que ya aplica su propia
     * validación de JWT. Exigir autenticación aquí también solo duplicaría esa verificación
     * en una capa que no tiene forma de validar los mismos tokens sin reimplementar JwtService.
     *
     * @param http el builder reactivo de seguridad HTTP
     * @return la cadena de filtros de seguridad, permitiendo todos los exchanges hacia las rutas
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // NOSONAR java:S4502 -- stateless gateway, no session cookies
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll())
                .build();
    }
}
