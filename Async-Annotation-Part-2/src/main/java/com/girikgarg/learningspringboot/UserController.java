package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RestController
public class UserController {
    @Autowired
    private AsyncLogger asyncLogger;

    @GetMapping("/users")
    public String getUsers() {
        System.out.println("User controller running on Thread " + Thread.currentThread().getName());
        Future<String> asyncLoggerFuture = asyncLogger.log();

        System.out.println("Main thread continuing its execution");

        try {
            String result = asyncLoggerFuture.get();
            System.out.println("Result of future is "+ result);
        }
        catch (Exception e) {
            System.out.println("Exception while fetching result of future");
        }

        return "Hello";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable String id) {
        System.out.println("User controller running on Thread " + Thread.currentThread().getName());
//        CompletableFuture<String> asyncPrint = asyncLogger.print();

//        CompletableFuture<String> asyncPrint = asyncLogger.errorTesting();

        asyncLogger.errorTesting2();

        System.out.println("Main thread continuing its execution");
//
//        try {
//            String result = asyncPrint.get();
//            System.out.println("Result of completable future is "+ result);
//        }
//        catch (Exception e) {
//            System.out.println("Exception while fetching result of completable future" + e);
//        }

        return "Hi";
    }
}
