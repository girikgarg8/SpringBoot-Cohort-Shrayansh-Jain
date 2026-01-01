package com.concepts;

import com.concepts.config.ImmutableServerSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main Application Class
 * 
 * @EnableConfigurationProperties - Enables @ConfigurationProperties beans without @Component
 * This is required for IMMUTABLE configuration classes that use constructor binding.
 * 
 * Alternative: Use @ConfigurationPropertiesScan to scan all @ConfigurationProperties in package
 */
@SpringBootApplication
@EnableConfigurationProperties(ImmutableServerSettings.class)
public class OrderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderserviceApplication.class, args);
	}

}

