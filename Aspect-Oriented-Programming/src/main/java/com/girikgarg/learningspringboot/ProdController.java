package com.girikgarg.learningspringboot;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("prod")
public class ProdController {
    public ProdController() {
        System.out.println("ProdController constructor");
    }
}
