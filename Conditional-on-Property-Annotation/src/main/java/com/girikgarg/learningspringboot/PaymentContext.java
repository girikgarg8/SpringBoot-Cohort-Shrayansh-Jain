package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PaymentContext {
    @Autowired(required=false)
    private UPIPaymentStrategy upiPaymentStrategy;

    @PostConstruct
    public void init() {
        System.out.println("Upi payment strategy is null: " + Objects.isNull(upiPaymentStrategy));
    }
}
