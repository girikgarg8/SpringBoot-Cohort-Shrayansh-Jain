package com.girikgarg.learningspringboot;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    
    @GetMapping("/session-info")
    public Map<String, Object> getSessionInfo(Authentication auth, HttpSession session) {
        Map<String, Object> info = new HashMap<>();
        info.put("username", auth.getName());
        info.put("sessionId", session.getId());
        info.put("creationTime", session.getCreationTime());
        info.put("lastAccessedTime", session.getLastAccessedTime());
        info.put("maxInactiveInterval", session.getMaxInactiveInterval());
        return info;
    }
    
    @GetMapping("/")
    public String home(Authentication auth) {
        return "Welcome " + auth.getName() + "! Visit /session-info for details or /h2-console to view sessions in DB";
    }
}
