package com.concepts.retryer;

import feign.RetryableException;
import feign.Retryer;

// UserCase-2: Want full control, then implement Retryer itself and 
// provide the custom implementation for the "continueOrPropagate()" method.
public class MyCustomRetryerFullControl implements Retryer {

    private int attempt = 1;
    private final int maxAttempts = 5;

    @Override
    public void continueOrPropagate(RetryableException e) {
        System.out.println("=== MyCustomRetryerFullControl - continueOrPropagate() invoked ===");
        System.out.println("Attempt: " + attempt + " / " + maxAttempts);
        System.out.println("Exception: " + e.getMessage());
        
        // Your custom logic to check if attempt increases the max attempt
        // then throw exception
        if (attempt >= maxAttempts) {
            System.out.println("✗ Max attempts reached! Throwing exception...");
            throw e;
        }
        
        attempt++;
        
        try {
            System.out.println("⏳ Waiting 100ms before retry...");
            Thread.sleep(100);  // Wait 100ms before retry
        } catch (InterruptedException ie) {
            // Do something
            System.err.println("Retry interrupted: " + ie.getMessage());
        }
        
        System.out.println("=======================================================");
    }

    @Override
    public Retryer clone() {
        return new MyCustomRetryerFullControl();
    }
}


