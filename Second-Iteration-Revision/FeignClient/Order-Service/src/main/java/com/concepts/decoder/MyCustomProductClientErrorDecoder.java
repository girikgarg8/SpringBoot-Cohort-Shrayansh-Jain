package com.concepts.decoder;

import com.concepts.exception.MyCustomBadRequestException;
import com.concepts.exception.MyCustomServerException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class MyCustomProductClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        
        System.out.println("=== Custom Error Decoder Invoked ===");
        System.out.println("Method Key: " + methodKey);
        System.out.println("Response Status: " + response.status());
        
        // Get the HTTP status code
        int statusCode = response.status();
        
        if (statusCode >= 400 && statusCode < 500) {
            System.out.println("4xx Client Error detected - Throwing MyCustomBadRequestException");
            return new MyCustomBadRequestException("Client Error");
        } else if (statusCode >= 500 && statusCode < 600) {
            System.out.println("5xx Server Error detected - Throwing MyCustomServerException");
            return new MyCustomServerException("Server Error");
        } else {
            System.out.println("Using default error decoder for status: " + statusCode);
            return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}


