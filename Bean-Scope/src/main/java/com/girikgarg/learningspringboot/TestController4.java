package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v4")
//@Scope("prototype")
@Scope("singleton")
public class TestController4 {

    @Autowired
    private DependencyA dependencyA;

    @Autowired
    private DependencyB dependencyB;

    @PostConstruct
    public void init() {
        System.out.println("🚀 TestController4 @PostConstruct - Controller created at startup");
        System.out.println("🚀 Thread: " + Thread.currentThread().getName());
        System.out.println("🚀 DependencyA class: " + dependencyA.getClass().getName());
        System.out.println("🚀 DependencyB class: " + dependencyB.getClass().getName());
        System.out.flush();
        // ❌ REMOVED: This was forcing request-scoped beans to be created at startup!
        // System.out.println("a's hashcode injected into controller4 is " + dependencyA.hashCode());
    }

    @GetMapping("/users")
    public String getUsers() {
        System.out.println("\n🌐 === HTTP Request Started ===");
        System.out.println("🌐 Thread: " + Thread.currentThread().getName());
        System.out.flush();
        
        // ✅ FIRST access to request-scoped beans - this will trigger their creation
        System.out.println("🌐 About to access DependencyA for the first time in this request...");
        System.out.println("🌐 DependencyA class: " + dependencyA.getClass().getName());
        System.out.flush();
        
        // ✅ Call a method to trigger actual bean creation
        System.out.println("🌐 Calling DependencyA.doSomething()...");
        dependencyA.doSomething();
        System.out.flush();
        
        int hashA = dependencyA.hashCode();
        System.out.println("🌐 DependencyA hashcode: " + hashA);
        System.out.flush();
        
        System.out.println("🌐 About to access DependencyB for the first time in this request...");
        System.out.println("🌐 DependencyB class: " + dependencyB.getClass().getName());
        System.out.flush();
        
        int hashB = dependencyB.hashCode();
        System.out.println("🌐 DependencyB hashcode: " + hashB);
        System.out.flush();
        
        // ✅ Now test the interaction between DependencyB and DependencyA
        System.out.println("🌐 Testing DependencyB -> DependencyA interaction...");
        dependencyB.accessDependencyA();
        System.out.flush();
        
        System.out.println("🌐 === HTTP Request Processing Complete ===\n");
        System.out.flush();
        
        return "Hello world - Check console for constructor logs!";
    }
}
