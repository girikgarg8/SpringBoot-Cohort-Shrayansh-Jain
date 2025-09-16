package com.girikgarg.learningspringboot;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSessionController {

    @GetMapping("/clear-session")
    public String clearSession(HttpSession session) {
        session.invalidate();
        return "session-cleared"; // Or redirect to a login page, etc.
    }
}
