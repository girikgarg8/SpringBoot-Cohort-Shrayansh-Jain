package com.concepts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * Configuration Properties class to hold custom properties from Config Server
 * 
 * @RefreshScope: When POST /actuator/refresh is called, this bean will be destroyed
 *                and recreated with fresh property values from Config Server
 *                WITHOUT restarting the application!
 * 
 * @ConfigurationProperties: Binds properties with prefix "custom" from config server
 *                          (e.g., custom.message from order-service-dev.properties)
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "custom")
public class OrderProperties {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

