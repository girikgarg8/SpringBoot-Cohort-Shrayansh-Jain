package com.concepts.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class MyCustomProductClientDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        System.out.println("=== Custom Decoder Invoked ===");
        System.out.println("Response status: " + response.status());
        System.out.println("Response type: " + type);
        
        // Reading raw response body
        InputStream responseBody = response.body().asInputStream();
        
        System.out.println("Reading response body from InputStream...");
        
        // Parsing JSON and converting to Java object type
        Object decodedObject = new ObjectMapper().readValue(responseBody, 
            new com.fasterxml.jackson.core.type.TypeReference<Object>() {
                @Override
                public Type getType() {
                    return type;
                }
            });
        
        System.out.println("Decoded object: " + decodedObject);
        System.out.println("================================");
        
        return decodedObject;
    }
}

