package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class Booking {
    @Autowired
    private User user;

    @GetMapping("/bookings")
    public String print() {
        System.out.println("User ID is " + user.getId());
        return user.getId();
    }
}
