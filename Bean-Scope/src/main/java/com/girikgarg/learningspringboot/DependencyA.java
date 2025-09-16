package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DependencyA {
    public DependencyA() {
        System.out.println("🔥 DependencyA constructor called! Thread: " + Thread.currentThread().getName());
        System.out.flush(); // Force immediate output
    }

    @PostConstruct
    public void init() {
        System.out.println("🔥 DependencyA @PostConstruct called! Thread: " + Thread.currentThread().getName());
        System.out.flush(); // Force immediate output
    }
    
    // ✅ Add a method to force actual bean creation
    public void doSomething() {
        System.out.println("🔥 DependencyA doSomething() called! Thread: " + Thread.currentThread().getName());
    }
}
