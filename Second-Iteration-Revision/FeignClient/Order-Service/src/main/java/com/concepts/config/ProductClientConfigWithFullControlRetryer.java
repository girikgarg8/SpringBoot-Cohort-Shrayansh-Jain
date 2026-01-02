package com.concepts.config;

import com.concepts.retryer.MyCustomRetryerFullControl;
import feign.Response;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import java.util.Date;

// Configuration for Feign Client with full control Retryer (UseCase-2)
public class ProductClientConfigWithFullControlRetryer {

    @Bean
    public Retryer myCustomRetryerFullControl() {
        System.out.println("Creating custom retryer bean (UseCase-2 - full control)");
        return new MyCustomRetryerFullControl();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, Response response) {
                // Make 503 errors retryable
                if (response.status() == 503) {
                    System.out.println("ErrorDecoder: 503 detected - making it retryable");
                    return new RetryableException(
                            response.status(),
                            "Service Unavailable - will retry",
                            response.request().httpMethod(),
                            new Date(),
                            response.request()
                    );
                }
                return new ErrorDecoder.Default().decode(methodKey, response);
            }
        };
    }
}

