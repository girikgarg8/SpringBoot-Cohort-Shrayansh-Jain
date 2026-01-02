package com.concepts.config;

import com.concepts.decoder.MyCustomProductClientDecoder;
import com.concepts.decoder.MyCustomProductClientErrorDecoder;
import com.concepts.encoder.MyCustomProductClientEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

// IMPORTANT: Don't use @Configuration here!
// If we use @Configuration, these beans will be registered globally 
// and applied to ALL Feign clients, not just ProductClientWithCustomConfig
public class ProductClientConfig {

    @Bean
    public Encoder myCustomEncoder() {
        System.out.println("Creating custom encoder bean");
        return new MyCustomProductClientEncoder();
    }

    @Bean
    public Decoder myCustomDecoder() {
        System.out.println("Creating custom decoder bean");
        return new MyCustomProductClientDecoder();
    }

    @Bean
    public ErrorDecoder myCustomErrorDecoder() {
        System.out.println("Creating custom error decoder bean");
        return new MyCustomProductClientErrorDecoder();
    }
}


