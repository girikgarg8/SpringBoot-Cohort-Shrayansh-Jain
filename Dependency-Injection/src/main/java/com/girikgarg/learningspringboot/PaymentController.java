package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentController {
    @Autowired
    @Qualifier("upiPaymentStrategy")
    private PaymentStrategy paymentStrategy;
}
