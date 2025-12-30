package com.girikgarg.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ProductClient productClient;
    
    @GetMapping
    public String getAllOrders() {
        return "All orders from Order Service";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Order Service is running on port 8083";
    }
    
    @GetMapping("/call-product-resttemplate/{id}")
    public String callProductAPI(@PathVariable String id) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("product-service");
        URI uri = instances.get(0).getUri();
        
        String response = restTemplate.getForObject(uri + "/products/" + id, String.class);
        
        return "Order Service called Product Service using DiscoveryClient + RestTemplate: " + response;
    }
    
    @GetMapping("/call-product-loadbalanced/{id}")
    public String callProductWithLoadBalancer(@PathVariable String id) {
        String response = restTemplate.getForObject("http://product-service/products/" + id, String.class);
        return "Order Service called Product Service using LoadBalanced RestTemplate: " + response;
    }
    
    @GetMapping("/call-product-feign/{id}")
    public String callProductWithFeign(@PathVariable String id) {
        String response = productClient.getProductById(id);
        return "Order Service called Product Service using Feign Client: " + response;
    }
    
    @GetMapping("/call-product-feign-all")
    public String callProductAllWithFeign() {
        String response = productClient.getAllProducts();
        return "Order Service called Product Service using Feign Client: " + response;
    }
}

