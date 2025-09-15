package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestC {
    @Autowired
    private TestD testD;

    @PostConstruct
    public void init() {
        testD.setTestC(this);
    }
}
