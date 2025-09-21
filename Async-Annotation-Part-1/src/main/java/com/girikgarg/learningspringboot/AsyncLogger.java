package com.girikgarg.learningspringboot;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncLogger {
    @Async
    public void log() {
        System.out.println("AsyncLogger running on thread " + Thread.currentThread().getName());
    }
}
