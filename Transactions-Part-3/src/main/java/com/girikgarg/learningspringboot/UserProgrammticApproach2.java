package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Component
public class UserProgrammticApproach2 {
    TransactionTemplate transactionTemplate;

    @Autowired
    public UserProgrammticApproach2(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void updateUser() {
        TransactionCallback <TransactionStatus> dbOperationsTask = (TransactionStatus status) -> {
            System.out.println("Insert query ran");
            System.out.println("Update query ran");
            return status;
        };

        TransactionStatus status = transactionTemplate.execute(dbOperationsTask);
    }

}

