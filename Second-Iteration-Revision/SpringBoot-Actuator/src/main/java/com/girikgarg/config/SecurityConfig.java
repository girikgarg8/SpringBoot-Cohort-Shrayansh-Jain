package com.girikgarg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication required
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                        // Allow only GET requests (READ operations) on custom endpoint without authentication
                        .requestMatchers(HttpMethod.GET, "/actuator/my-custom-stats/**").permitAll()
                        // POST and DELETE operations require authentication
                        .requestMatchers(HttpMethod.POST, "/actuator/my-custom-stats/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/actuator/my-custom-stats/**").authenticated()
                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // Basic auth for simplicity
                .csrf(csrf -> csrf.disable()); // Disable CSRF for testing POST/DELETE operations
        
        return http.build();
    }
}

