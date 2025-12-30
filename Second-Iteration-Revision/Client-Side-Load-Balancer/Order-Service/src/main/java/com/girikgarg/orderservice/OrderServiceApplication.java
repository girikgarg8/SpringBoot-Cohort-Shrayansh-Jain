package com.girikgarg.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
// @LoadBalancerClients allows defining both default and specific configurations
// First child clients config will be loaded, then default one
// So for "product-service" -> RandomLoadBalancer (from LoadBalancerProductClientConfig)
// For all other services -> RoundRobinLoadBalancer (from LoadBalancerGlobalConfig - default)
@LoadBalancerClients(
    defaultConfiguration = LoadBalancerGlobalConfig.class,
    value = {
        @LoadBalancerClient(name = "product-service", configuration = LoadBalancerProductClientConfig.class)
    }
)
public class OrderServiceApplication {
    
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}

