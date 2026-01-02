package com.concepts.interceptor;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class MyCustomRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        
        // Add custom header to the request
        request.getHeaders().add("x-custom-header", "myvalue");
        
        System.out.println("=== Custom Interceptor Invoked ===");
        System.out.println("Adding custom header: x-custom-header = myvalue");
        System.out.println("Request URI: " + request.getURI());
        
        // Continue with the request execution
        return execution.execute(request, body);
    }
}

