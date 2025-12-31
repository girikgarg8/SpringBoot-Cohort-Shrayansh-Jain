package com.girikgarg.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom Actuator Endpoint to demonstrate read, write, and delete operations
 * 
 * This endpoint will be available at: /actuator/my-custom-stats (or /manage/my-custom-stats if base-path is /manage)
 * 
 * @Endpoint annotation makes this a custom actuator endpoint
 * id parameter defines the URL path segment for this endpoint
 */
@Component
@Endpoint(id = "my-custom-stats")
public class MyCustomStatsEndpoint {

    // In-memory storage for demo purposes
    // In production, this would be backed by a database or cache
    private final Map<String, String> statsStore = new HashMap<>();

    /**
     * READ Operation - Handles GET requests
     * 
     * This method is invoked when you access: GET /actuator/my-custom-stats
     * 
     * @return a simple message
     */
    @ReadOperation
    public String readAll() {
        return "Hello, Spring Boot!";
    }

    /**
     * READ Operation with Path Parameters - Handles GET requests with selectors
     * 
     * This method is invoked when you access: GET /actuator/my-custom-stats/{name}/{message}
     * Example: GET /actuator/my-custom-stats/shrayansh/how%20are%20you
     * 
     * @Selector annotation binds URL path segments to method parameters
     * The selector follows the sequence: /my-custom-stats/{name}/{message}
     * 
     * @param name - first path parameter
     * @param message - second path parameter
     * @return personalized message
     */
    @ReadOperation
    public String read(@Selector String name, @Selector String message) {
        return "Hello: " + name + " msg for you is: " + message;
    }

    /**
     * WRITE Operation - Handles POST requests
     * 
     * This method is invoked when you access: POST /actuator/my-custom-stats
     * 
     * POST operations require authentication by default (configured in SecurityConfig)
     * This simulates a cache refresh operation
     * 
     * @return confirmation message
     */
    @WriteOperation
    public String refresh() {
        // Simulate cache refresh operation
        // In production, you would actually refresh cache, reload config, etc.
        statsStore.clear();
        statsStore.put("lastRefreshed", String.valueOf(System.currentTimeMillis()));
        return "refreshed!";
    }

    /**
     * DELETE Operation - Handles DELETE requests
     * 
     * This method is invoked when you access: DELETE /actuator/my-custom-stats/{key}
     * Example: DELETE /actuator/my-custom-stats/myKey
     * 
     * DELETE operations require authentication by default (configured in SecurityConfig)
     * 
     * @param key - the key to remove from stats store
     * @return confirmation message with the deleted key
     */
    @DeleteOperation
    public String remove(@Selector String key) {
        // Remove the specified key from the stats store
        // In production, this could be removing a cache entry, metric, etc.
        statsStore.remove(key);
        return "reset done for key: " + key;
    }

}

