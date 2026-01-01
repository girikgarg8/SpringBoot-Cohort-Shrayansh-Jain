package com.concepts.controller;

import com.concepts.config.UserConfigurations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller to expose the dynamically refreshed UserConfigurations.
 * This controller itself is NOT @RefreshScope'd, so its state (if any) would persist,
 * but it will always fetch the latest UserConfigurations bean.
 * 
 * IMPORTANT: We cannot return @RefreshScope beans directly because they are CGLIB proxies
 * that Jackson cannot serialize. Instead, we return the actual property values or create DTOs.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserConfigurations userConfigurations;

    /**
     * Returns all user configuration as a plain Map (not the @RefreshScope proxy bean).
     * This avoids Jackson serialization issues with CGLIB proxies.
     */
    @GetMapping("/config")
    public Map<String, Object> getUserConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("adminEmail", userConfigurations.getAdminEmail());
        config.put("featureFlags", userConfigurations.getFeatureFlags());
        config.put("users", userConfigurations.getUsers());
        config.put("address", userConfigurations.getAddress());
        config.put("preferences", userConfigurations.getPreferences());
        config.put("courses", userConfigurations.getCourses());
        config.put("locations", userConfigurations.getLocations());
        return config;
    }
    
    @GetMapping("/address")
    public UserConfigurations.AddressConfig getAddress() {
        return userConfigurations.getAddress();
    }
    
    @GetMapping("/preferences")
    public Map<String, String> getPreferences() {
        return userConfigurations.getPreferences();
    }
    
    @GetMapping("/courses")
    public List<UserConfigurations.Course> getCourses() {
        return userConfigurations.getCourses();
    }
    
    @GetMapping("/locations")
    public Map<String, UserConfigurations.AddressConfig> getLocations() {
        return userConfigurations.getLocations();
    }

    @GetMapping("/admin-email")
    public String getAdminEmail() {
        return "Admin Email: " + userConfigurations.getAdminEmail();
    }

    @GetMapping("/feature-flags")
    public List<String> getFeatureFlags() {
        return userConfigurations.getFeatureFlags();
    }

    @GetMapping("/all-users")
    public List<Map<String, String>> getAllUsers() {
        return userConfigurations.getUsers();
    }
}

