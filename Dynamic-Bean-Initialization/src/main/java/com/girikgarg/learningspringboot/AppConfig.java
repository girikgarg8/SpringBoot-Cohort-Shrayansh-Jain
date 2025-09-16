package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public PaymentStrategy createPaymentStrategy(@Value("${isUpi}") boolean isUpi) {
        if (isUpi) {
            return new UPIPaymentStrategy();
        }
        else return new CreditCardPaymentStrategy();
    }
}
