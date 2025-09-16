package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DependencyB {
    @Autowired
    private DependencyA dependencyA;

    public DependencyB() {
        System.out.println("🔥 DependencyB constructor called! Thread: " + Thread.currentThread().getName());
        System.out.flush(); // Force immediate output
    }

    @PostConstruct
    public void init() {
        System.out.println("🔥 DependencyB @PostConstruct called! Thread: " + Thread.currentThread().getName());
        // ❌ REMOVED: This was forcing DependencyA to be created during startup!
        // System.out.println("🔥 a's hashcode injected into b is " + dependencyA.hashCode());
        System.out.flush(); // Force immediate output
    }
    
    // ✅ Add a method to access DependencyA when needed
    public void accessDependencyA() {
        System.out.println("🔥 DependencyB accessing DependencyA: " + dependencyA.hashCode());
    }
}
