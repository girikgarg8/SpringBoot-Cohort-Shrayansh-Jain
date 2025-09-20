package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class UserProgrammaticApproach1 {
    private PlatformTransactionManager transactionManager;

    @Autowired
    public UserProgrammaticApproach1(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void updateUser() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE); // Most strict isolation
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // Always start a new transaction
        def.setTimeout(30); // 30 seconds timeout

        TransactionStatus status = transactionManager.getTransaction(def);

        try {
            System.out.println("Insert query run1");
            transactionManager.commit(status);
        }
        catch (Exception e) {
            transactionManager.rollback(status);
        }
    }
}
