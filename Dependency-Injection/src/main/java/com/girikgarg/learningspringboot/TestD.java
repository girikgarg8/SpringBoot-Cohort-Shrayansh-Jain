package com.girikgarg.learningspringboot;

import org.springframework.stereotype.Component;

@Component
public class TestD {
    private TestC testC;

    public void setTestC(TestC testC) {
        this.testC = testC;
    }

}
