package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Value("${app.env}")
    private String appEnvironment;
    
    @GetMapping("/environment")

    public String getUsers() {
        
        return "Environment is: " + appEnvironment;
    }
}
