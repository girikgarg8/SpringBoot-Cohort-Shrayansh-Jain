package com.concepts.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;

import java.lang.reflect.Type;

public class MyCustomProductClientEncoder implements Encoder {

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
        System.out.println("=== Custom Encoder Invoked ===");
        System.out.println("Encoding object: " + object);
        System.out.println("Body type: " + bodyType);
        
        // Manually converting object to JSON
        try {
            String jsonString = new ObjectMapper().writeValueAsString(object);
            System.out.println("Encoded JSON: " + jsonString);
            template.body(jsonString);
        } catch (Exception e) {
            System.err.println("Error encoding object: " + e.getMessage());
            throw new EncodeException("Unable to encode object", e);
        }
        
        System.out.println("================================");
    }
}

