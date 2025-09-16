package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component
//@Lazy
////@Primary
//@Qualifier("upiPaymentStrategy")
public class UPIPaymentStrategy implements PaymentStrategy {
    public UPIPaymentStrategy() {
        System.out.println("UPIPaymentStrategy initializing");
    }

    @Override
    public String pay() {
        return "Paying by UPI";
    }
}
