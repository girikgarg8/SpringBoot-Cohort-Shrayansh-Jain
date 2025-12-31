package com.girikgarg.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isDBUp = checkDBConnection();
        return isDBUp ? Health.up().withDetail("DB", "Available").build()
                      : Health.down().withDetail("DB", "Not-Available").build();
    }

    private boolean checkDBConnection() {
        // Check if DB is up or not
        // In a real scenario, you would actually check database connectivity
        // For demo purposes, returning true
        return true;
    }
}

