package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/csrf")
public class CsrfController {

    @GetMapping("/home")
    public String home() {
        System.out.println("=== User accessed home page (authenticated) ===");
        return "csrf-home";
    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestParam String amount, @RequestParam String to) {
        System.out.println("==============================================");
        System.out.println("CSRF ATTACK SUCCESSFUL!");
        System.out.println("==============================================");
        System.out.println("Money Transfer Executed:");
        System.out.println("  Amount: $" + amount);
        System.out.println("  To: " + to);
        System.out.println("==============================================");
        System.out.println("⚠️  This happened because CSRF protection is disabled!");
        System.out.println("==============================================\n");
        
        return "csrf-success";
    }
}

