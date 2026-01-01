package com.concepts.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

/**
 * Configuration properties for user-related settings.
 * This class demonstrates binding complex structures from configuration:
 * - Simple properties (String, List<String>)
 * - List of Maps (users)
 * - Nested static class (AddressConfig) for structured nested properties
 *
 * @RefreshScope ensures that this bean is recreated when /actuator/refresh is called,
 * allowing dynamic updates to user configurations without application restart.
 * 
 * @Validated enables validation of @ConfigurationProperties fields.
 * If any validation fails, application will fail to startup with detailed error messages.
 */
@Component
@RefreshScope
@Validated
@ConfigurationProperties(prefix = "user-config")
public class UserConfigurations {

    // @NotBlank - Cannot be null or empty (trims whitespace)
    // Example: "" or "   " will fail validation
    @NotBlank(message = "Admin email must not be empty")
    @Email(message = "Admin email must be a valid email format")
    private String adminEmail;
    
    // @NotEmpty - List must have at least one element
    // Example: [] will fail validation
    @NotEmpty(message = "Feature flags list cannot be empty")
    private List<String> featureFlags;
    
    // List of users - no validation on outer list, but could add @NotNull
    private List<Map<String, String>> users;
    
    // @Valid - Enables nested validation on AddressConfig fields
    // Without @Valid, validations inside AddressConfig won't be checked
    @Valid
    private AddressConfig address;
    
    // Map<String, String> - simple key-value pairs
    private Map<String, String> preferences;
    
    // @Valid - Triggers validation on each Course object in the list
    @Valid
    private List<Course> courses;
    
    // Map of nested static class objects - Map<String, Object>
    private Map<String, AddressConfig> locations;

    /**
     * Nested static class for binding complex nested configuration.
     * 
     * WHY STATIC IS REQUIRED:
     * 
     * Non-static inner class problem:
     * - Java compiler adds a HIDDEN FIRST PARAMETER (outer class instance) to all constructors
     * - Even the "no-arg" constructor becomes: AddressConfig(UserConfigurations outer)
     * - This is NOT a true no-arg constructor at bytecode level
     * - Spring's reflection-based binder looks for: AddressConfig.class.getDeclaredConstructor()
     * - It finds NO true no-arg constructor → binding fails
     * 
     * Static nested class solution:
     * - No implicit outer class reference added
     * - Default constructor is truly no-arg: AddressConfig()
     * - Spring's reflection works: new AddressConfig() → binding succeeds
     * 
     * Configuration format:
     *   user-config.address.city=myCityName
     *   user-config.address.country=myCountryName
     */
    public static class AddressConfig {
        // @NotBlank - City cannot be null, empty or only whitespace
        @NotBlank(message = "City must not be empty")
        private String city;
        
        // @NotBlank - Country cannot be null, empty or only whitespace
        @NotBlank(message = "Country must not be empty")
        private String country;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        @Override
        public String toString() {
            return "AddressConfig{" +
                    "city='" + city + '\'' +
                    ", country='" + country + '\'' +
                    '}';
        }
    }

    /**
     * Nested static class for List of objects.
     * Demonstrates binding a list of complex objects.
     * 
     * Configuration format:
     *   user-config.courses[0].name=Java-Springboot
     *   user-config.courses[0].enrolled=true
     *   user-config.courses[1].name=Python-Django
     *   user-config.courses[1].enrolled=false
     */
    public static class Course {
        // @NotBlank - Course name cannot be null or empty
        @NotBlank(message = "Course name must not be empty")
        private String name;
        
        // @AssertTrue / @AssertFalse can be used for boolean validations
        // Here we're just demonstrating - no validation on enrolled
        private boolean enrolled;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnrolled() {
            return enrolled;
        }

        public void setEnrolled(boolean enrolled) {
            this.enrolled = enrolled;
        }

        @Override
        public String toString() {
            return "Course{" +
                    "name='" + name + '\'' +
                    ", enrolled=" + enrolled +
                    '}';
        }
    }

    // Getters and Setters

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public List<String> getFeatureFlags() {
        return featureFlags;
    }

    public void setFeatureFlags(List<String> featureFlags) {
        this.featureFlags = featureFlags;
    }

    public List<Map<String, String>> getUsers() {
        return users;
    }

    public void setUsers(List<Map<String, String>> users) {
        this.users = users;
    }

    public AddressConfig getAddress() {
        return address;
    }

    public void setAddress(AddressConfig address) {
        this.address = address;
    }

    public Map<String, String> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, String> preferences) {
        this.preferences = preferences;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Map<String, AddressConfig> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, AddressConfig> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "UserConfigurations{" +
                "adminEmail='" + adminEmail + '\'' +
                ", featureFlags=" + featureFlags +
                ", users=" + users +
                ", address=" + address +
                ", preferences=" + preferences +
                ", courses=" + courses +
                ", locations=" + locations +
                '}';
    }
}

