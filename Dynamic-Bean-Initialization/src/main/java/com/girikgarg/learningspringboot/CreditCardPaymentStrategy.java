package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

//@Component
//@Lazy
//@Qualifier("creditCardPaymentStrategy")
public class CreditCardPaymentStrategy implements PaymentStrategy {
    public CreditCardPaymentStrategy() {
        System.out.println("CreditCardPaymentStrategy initializing");
    }

    @Override
    public String pay() {
        return "Paying by credit card";
    }
}
