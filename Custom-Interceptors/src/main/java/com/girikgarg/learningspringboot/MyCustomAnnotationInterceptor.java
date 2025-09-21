package com.girikgarg.learningspringboot;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Component
@Aspect
public class MyCustomAnnotationInterceptor {
    @Around("@annotation(com.girikgarg.learningspringboot.MyCustomAnnotation)") // point cut expression
    public void invoke(ProceedingJoinPoint joinPoint) throws Throwable { // advice
        System.out.println("MyCustomAnnotationInterceptor do something before actual method");
        Method method = joinPoint.getSignature().getDeclaringType().getMethods()[0];

        if (method.isAnnotationPresent(MyCustomAnnotation.class)) {
            MyCustomAnnotation annotation = method.getAnnotation(MyCustomAnnotation.class);
            System.out.println("Name from annotation " + annotation.name());
        }

        joinPoint.proceed();
        System.out.println("MyCustomAnnotationInterceptor do something after actual method");
    }

}
