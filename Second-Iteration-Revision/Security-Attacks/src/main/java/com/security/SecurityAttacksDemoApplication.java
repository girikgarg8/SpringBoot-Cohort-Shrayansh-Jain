package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityAttacksDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityAttacksDemoApplication.class, args);
        System.out.println("\n==============================================");
        System.out.println("Security Attacks Demo Application Started!");
        System.out.println("==============================================");
        System.out.println("Default User Credentials:");
        System.out.println("  Username: user");
        System.out.println("  Password: password");
        System.out.println("==============================================\n");
    }
}

