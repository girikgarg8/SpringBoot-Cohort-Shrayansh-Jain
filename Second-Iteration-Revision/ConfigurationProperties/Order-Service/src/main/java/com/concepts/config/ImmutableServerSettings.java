package com.concepts.config;

import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * IMMUTABLE Server Settings Configuration.
 * 
 * Key differences from mutable configuration:
 * 
 * 1. NO @Component annotation - Bean creation is handled by @ConfigurationPropertiesScan
 * 2. Fields are FINAL - Cannot be changed after object construction
 * 3. NO setter methods - Values set only through constructor
 * 4. Constructor Binding - Spring invokes constructor with property values
 * 5. Only getter methods - Read-only access to properties
 * 
 * Why Constructor Binding makes it immutable:
 * - Spring IOC doesn't create empty bean first (no default constructor needed)
 * - Spring directly invokes the parameterized constructor with bound values
 * - Since fields are final, they can only be set once in constructor
 * - No setters = no way to modify after creation
 * 
 * Benefits of Immutability:
 * - Thread-safe (no mutable state)
 * - Easier to reason about (state never changes)
 * - Safer in multi-threaded environments
 * - Prevents accidental modifications
 * 
 * Note: In Spring Boot 3.x, @ConstructorBinding is NOT needed when there's only one constructor.
 * Spring automatically uses constructor binding.
 */
@Validated
@ConfigurationProperties(prefix = "immutable-server-settings")
public class ImmutableServerSettings {

    /**
     * FINAL fields - can only be set once in constructor
     */
    @NotBlank(message = "Environment name must not be empty")
    private final String environment;

    @Min(value = 1024, message = "Port must be at least 1024")
    @Max(value = 65535, message = "Port must be at most 65535")
    private final int port;

    @Positive(message = "Timeout must be a positive number")
    private final int timeoutSeconds;

    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}", message = "Server ID must match pattern: XX-9999 (e.g., US-1234)")
    private final String serverId;

    /**
     * Constructor Binding - Spring calls this constructor with property values.
     * 
     * Parameter names MUST match property names (after removing prefix):
     * - immutable-server-settings.environment → environment parameter
     * - immutable-server-settings.port → port parameter
     * - immutable-server-settings.timeout-seconds → timeoutSeconds parameter
     * - immutable-server-settings.server-id → serverId parameter
     * 
     * Spring automatically detects kebab-case to camelCase conversion.
     */
    public ImmutableServerSettings(
            String environment,
            int port,
            int timeoutSeconds,
            String serverId) {
        this.environment = environment;
        this.port = port;
        this.timeoutSeconds = timeoutSeconds;
        this.serverId = serverId;
    }

    // ONLY GETTERS - No setters = Immutable

    public String getEnvironment() {
        return environment;
    }

    public int getPort() {
        return port;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public String getServerId() {
        return serverId;
    }

    @Override
    public String toString() {
        return "ImmutableServerSettings{" +
                "environment='" + environment + '\'' +
                ", port=" + port +
                ", timeoutSeconds=" + timeoutSeconds +
                ", serverId='" + serverId + '\'' +
                '}';
    }
}

