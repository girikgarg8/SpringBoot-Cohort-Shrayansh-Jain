package com.concepts.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * Custom Route-Specific Gateway Filter with Configuration
 * 
 * This demonstrates how to create a custom filter that accepts configuration parameters
 * from application.properties
 */
@Component
public class CustomRouteGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomRouteGatewayFilterFactory.CustomConfig> {

    public CustomRouteGatewayFilterFactory() {
        super(CustomConfig.class);
    }

    @Override
    public GatewayFilter apply(CustomConfig config) {
        return (exchange, chain) -> {
            // Pre-processing logic
            System.out.println("=== Custom Route Filter - Pre-Processing ===");
            System.out.println("pre filter logic here, config value: " + config.getCountry());
            System.out.println("Request Path: " + exchange.getRequest().getURI().getPath());
            
            // Continue to next filter in chain
            return chain.filter(exchange).then(reactor.core.publisher.Mono.fromRunnable(() -> {
                // Post-processing logic
                System.out.println("=== Custom Route Filter - Post-Processing ===");
                System.out.println("post filter logic here");
                System.out.println("Response Status: " + exchange.getResponse().getStatusCode());
            }));
        };
    }

    /**
     * Configuration class to hold filter parameters
     * Spring automatically maps properties from application.properties to this class
     */
    public static class CustomConfig {
        private String country;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}

