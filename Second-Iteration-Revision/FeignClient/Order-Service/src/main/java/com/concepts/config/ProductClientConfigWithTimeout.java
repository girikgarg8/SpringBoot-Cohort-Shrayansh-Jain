package com.concepts.config;

import feign.Request;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

// Configuration for Feign Client with timeout settings
public class ProductClientConfigWithTimeout {

    @Bean
    public Request.Options requestOptions() {
        System.out.println("=== Creating Request.Options with custom timeout ===");
        System.out.println("Connect Timeout: 1000ms (1 second)");
        System.out.println("Read Timeout: 2000ms (2 seconds)");
        
        // connectTimeout, readTimeout (in milliseconds)
        return new Request.Options(
                1000,  // connectTimeout: 1 second
                TimeUnit.MILLISECONDS,
                2000,  // readTimeout: 2 seconds
                TimeUnit.MILLISECONDS,
                true   // followRedirects
        );
    }
}

