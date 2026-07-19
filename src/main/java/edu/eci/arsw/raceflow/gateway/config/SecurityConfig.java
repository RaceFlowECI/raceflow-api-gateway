package edu.eci.arsw.raceflow.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/** WebFlux security configuration for the gateway's reactive request pipeline. */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * The gateway itself does not authenticate requests -- each proxied route hits a
     * downstream service (auth-service, realtime-service) that already enforces its own
     * JWT validation. Requiring auth here too would just duplicate that check at a layer
     * that has no way to validate the same tokens without re-implementing JwtService.
     *
     * @param http the reactive HTTP security builder
     * @return the security filter chain, permitting all exchanges through to the routes
     */
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // NOSONAR java:S4502 -- stateless gateway, no session cookies
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll())
                .build();
    }
}
