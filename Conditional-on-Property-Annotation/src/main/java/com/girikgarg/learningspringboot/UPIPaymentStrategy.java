package com.girikgarg.learningspringboot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix="application", value="isUPIEnabled", havingValue="true", matchIfMissing = true)
public class UPIPaymentStrategy implements PaymentStrategy {

    public UPIPaymentStrategy() {
        System.out.println("UPI Payment strategy constructor called");
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("You have paid " + amount + "by UPI");
        return true;
    }
}
