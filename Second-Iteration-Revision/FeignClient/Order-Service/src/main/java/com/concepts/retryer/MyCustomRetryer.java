package com.concepts.retryer;

import feign.RetryableException;
import feign.Retryer;

// UserCase-1: Only want to control Attempt, wait time and max period, 
// rest want to reuse the "Retryer.Default" logic.
public class MyCustomRetryer extends Retryer.Default {

    private int attemptCount = 0;

    // Just need to control the attempts, wait time only, rest using Default implementation
    public MyCustomRetryer() {
        super(200,  // period: initial wait time in ms
              1000, // maxPeriod: maximum wait time in ms
              6);   // maxAttempts: maximum number of attempts
        
        System.out.println("MyCustomRetryer created with period=200ms, maxPeriod=1000ms, maxAttempts=6");
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        attemptCount++;
        System.out.println("=== MyCustomRetryer - Retry Attempt #" + attemptCount + " ===");
        System.out.println("Exception: " + e.getMessage());
        System.out.println("Status: " + e.status());
        
        try {
            super.continueOrPropagate(e);
            System.out.println("Will retry after waiting...");
        } catch (RetryableException re) {
            System.out.println("✗ Max retries reached. Propagating exception.");
            throw re;
        }
    }

    @Override
    public Retryer clone() {
        return new MyCustomRetryer();
    }
}

