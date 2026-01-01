package com.concepts.controller;

import com.concepts.config.ServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to expose server settings.
 */
@RestController
@RequestMapping("/server-settings")
public class ServerSettingsController {

    @Autowired
    private ServerSettings serverSettings;

    @GetMapping
    public ServerSettings getServerSettings() {
        return serverSettings;
    }

    @GetMapping("/environment")
    public String getEnvironment() {
        return "Environment: " + serverSettings.getEnvironment();
    }

    @GetMapping("/port")
    public String getPort() {
        return "Port: " + serverSettings.getPort();
    }

    @GetMapping("/timeout")
    public String getTimeout() {
        return "Timeout: " + serverSettings.getTimeoutSeconds() + " seconds";
    }

    @GetMapping("/server-id")
    public String getServerId() {
        return "Server ID: " + serverSettings.getServerId();
    }
}

