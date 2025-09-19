package com.girikgarg.learningspringboot;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
public class DevController {
    public DevController() {
        System.out.println("DevController constructor");
    }
}
