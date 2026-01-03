package com.concepts.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    // @Bean
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }
    
    // Or, use below if we want to set timeouts too
    @Bean
    public RestTemplate restTemplate() {
        
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        
        // Set the timeouts in millisecond
        factory.setConnectTimeout(1000);  // 1 sec for connection timeout
        factory.setReadTimeout(5000);     // 5 sec for response timeout
        
        return new RestTemplate(factory);
    }
}


