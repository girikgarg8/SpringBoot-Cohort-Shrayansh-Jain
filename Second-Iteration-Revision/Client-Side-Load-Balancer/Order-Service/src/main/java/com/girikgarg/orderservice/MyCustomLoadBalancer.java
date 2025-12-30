package com.girikgarg.orderservice;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

public class MyCustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    
    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceSuppliers;
    private final String serviceId;
    
    public MyCustomLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceSuppliers,
                                String serviceId) {
        this.serviceInstanceSuppliers = serviceInstanceSuppliers;
        this.serviceId = serviceId;
    }
    
    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        return serviceInstanceSuppliers.getIfAvailable().get().next().map(instances -> {
            if (instances == null || instances.isEmpty()) {
                return new EmptyResponse();
            }
            
            // Custom load balancing algorithm - just picking first instance for demo
            return new DefaultResponse(instances.get(0));
        });
    }
}

