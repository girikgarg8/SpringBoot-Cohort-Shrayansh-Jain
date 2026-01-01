package com.concepts.config;

import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Server settings configuration - LOCAL ONLY properties.
 * These properties are NOT fetched from Config Server, only from local application.properties.
 * 
 * This class demonstrates various validation constraints:
 * 1. @NotBlank - String cannot be null, empty, or whitespace
 * 2. @Min/@Max - Numeric range validation
 * 3. @Positive - Must be > 0
 * 4. @Pattern - Regex pattern matching
 * 
 * @Validated enables validation. If any validation fails, application startup will fail.
 */
@Component
@Validated
@ConfigurationProperties(prefix = "server-settings")
public class ServerSettings {

    /**
     * @NotBlank - Cannot be null, empty string (""), or only whitespace ("   ")
     * Example failures: null, "", "  "
     * Example success: "production", "dev"
     */
    @NotBlank(message = "Environment name must not be empty")
    private String environment;

    /**
     * @Min - Value must be >= 1024
     * @Max - Value must be <= 65535
     * Example failures: 0, 100, 70000
     * Example success: 8080, 3000, 9090
     */
    @Min(value = 1024, message = "Port must be at least 1024")
    @Max(value = 65535, message = "Port must be at most 65535")
    private int port;

    /**
     * @Positive - Value must be > 0
     * Example failures: 0, -1, -100
     * Example success: 1, 30, 300
     */
    @Positive(message = "Timeout must be a positive number")
    private int timeoutSeconds;

    /**
     * @Pattern - Must match the regex pattern [A-Z]{2}-[0-9]{4}
     * Format: Two uppercase letters, hyphen, four digits
     * Example failures: "ab-1234", "AB-12", "AB1234"
     * Example success: "US-1234", "IN-5678"
     */
    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}", message = "Server ID must match pattern: XX-9999 (e.g., US-1234)")
    private String serverId;

    // Getters and Setters

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public String toString() {
        return "ServerSettings{" +
                "environment='" + environment + '\'' +
                ", port=" + port +
                ", timeoutSeconds=" + timeoutSeconds +
                ", serverId='" + serverId + '\'' +
                '}';
    }
}

