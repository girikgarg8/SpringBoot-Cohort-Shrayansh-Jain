package com.concepts.controller;

import com.concepts.config.ImmutableServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to expose immutable server settings.
 */
@RestController
@RequestMapping("/immutable-server-settings")
public class ImmutableServerSettingsController {

    @Autowired
    private ImmutableServerSettings immutableServerSettings;

    @GetMapping
    public ImmutableServerSettings getImmutableServerSettings() {
        return immutableServerSettings;
    }

    @GetMapping("/environment")
    public String getEnvironment() {
        return "Environment: " + immutableServerSettings.getEnvironment();
    }

    @GetMapping("/port")
    public String getPort() {
        return "Port: " + immutableServerSettings.getPort();
    }

    @GetMapping("/timeout")
    public String getTimeout() {
        return "Timeout: " + immutableServerSettings.getTimeoutSeconds() + " seconds";
    }

    @GetMapping("/server-id")
    public String getServerId() {
        return "Server ID: " + immutableServerSettings.getServerId();
    }
}

