package com.girikgarg.orderservice;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LoadBalancerGlobalConfig {
    
    @Bean
    @ConditionalOnMissingBean(ReactorLoadBalancer.class)
    public ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(
            LoadBalancerClientFactory factory,
            Environment environment) {
        
        String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        
        // This is the default/global load balancer strategy
        // Will be used for all services EXCEPT those with specific configurations
        // @ConditionalOnMissingBean ensures this bean is created only if no other ReactorLoadBalancer bean exists
        // So if a service has its own LoadBalancer config (like product-service), that takes precedence
        return new RoundRobinLoadBalancer(
                factory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class),
                serviceId
        );
    }
}

