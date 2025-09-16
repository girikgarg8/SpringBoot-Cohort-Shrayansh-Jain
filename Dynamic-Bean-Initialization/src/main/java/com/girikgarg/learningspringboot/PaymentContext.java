package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2")
public class PaymentContext {
    @Autowired
    private PaymentStrategy paymentStrategy;

    @PostMapping("/payments")
    public String doPayment() {
        return paymentStrategy.pay();
    }
}