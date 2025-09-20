package com.girikgarg.learningspringboot;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    // Pointcut matches the getUsers method in SampleController
    @Before("execution(* com.girikgarg.learningspringboot.SampleController.getUsers(..))")
    public void beforeGetUsers(JoinPoint joinPoint) {
        System.out.println("[Aspect] Before executing: " + joinPoint.getSignature());
    }

    @After("execution(* com.girikgarg.learningspringboot.SampleController.getUsers(..))")
    public void afterGetUsers(JoinPoint joinPoint) {
        System.out.println("[Aspect] After executing: " + joinPoint.getSignature());
    }
}
