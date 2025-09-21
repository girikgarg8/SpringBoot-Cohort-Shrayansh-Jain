package com.girikgarg.learningspringboot;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class AsyncLogger {
    @Async
    public Future <String> log() {
        System.out.println("AsyncLogger running on thread " + Thread.currentThread().getName());
        return new AsyncResult<>("Hello World");
    }

    @Async
    public CompletableFuture <String> print() {
        System.out.println("AsyncPrint running on thread " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture("Hi World");
    }

    @Async
    public CompletableFuture<String> errorTesting() {
        throw new RuntimeException("This is an error for testing");
    }

    @Async
    public void errorTesting2() {
        throw new RuntimeException("This is error for testing");
    }
}
