package com.girikgarg.learningspringboot;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleService {
    @Transactional(transactionManager = "customTransactionManager")
    public void performTransactionalOperation() {
        System.out.println("Inside transactional method. This would be a DB operation in a real app.");
        // Simulate some logic
    }
}
