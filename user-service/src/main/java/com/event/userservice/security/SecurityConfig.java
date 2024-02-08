package com.event.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationConverter jwtConverter,
            ObjectMapper objectMapper
    ) throws Exception {
        http
                .authorizeHttpRequests(requests -> {
                    requests.anyRequest().authenticated();
                })
                .oauth2ResourceServer(oauth2 -> {
                    oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter));
                })
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint((request, response, authException) -> {
                        Map<String, String> unauthorized = Map.of("status", "unauthorized");

                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getOutputStream()
                                .write(objectMapper.writeValueAsBytes(unauthorized));
                    });
                    exceptions.accessDeniedHandler((request, response, accessDeniedException) -> {
                        Map<String, String> forbidden = Map.of("status", "forbidden");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.getOutputStream()
                                .write(objectMapper.writeValueAsBytes(forbidden));
                    });
                })
        ;
        return http.build();
    }
}
