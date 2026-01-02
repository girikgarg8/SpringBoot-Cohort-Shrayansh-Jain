package com.concepts.config;

import com.concepts.interceptor.MyCustomRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .requestInterceptor(new MyCustomRequestInterceptor())
                .build();
    }

    @Bean
    public MyCustomRequestInterceptor customRequestInterceptor() {
        return new MyCustomRequestInterceptor();
    }
}
