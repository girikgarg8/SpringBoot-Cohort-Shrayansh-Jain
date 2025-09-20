package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class RequiredPropagationDemoService {
    @Autowired
    private InnerService innerService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void outerMethod() {
        System.out.println("Outer method started");
        System.out.println("isActualTransactionActive: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Current transaction name: " + TransactionSynchronizationManager.getCurrentTransactionName());
        innerService.innerMethod();
        System.out.println("Outer method finished");
    }

    @Service
    public static class InnerService {
        @Transactional(propagation = Propagation.REQUIRED)
        public void innerMethod() {
            System.out.println("Inner method executed");
            System.out.println("isActualTransactionActive: " + TransactionSynchronizationManager.isActualTransactionActive());
            System.out.println("Current transaction name: " + TransactionSynchronizationManager.getCurrentTransactionName());
        }
    }
}
