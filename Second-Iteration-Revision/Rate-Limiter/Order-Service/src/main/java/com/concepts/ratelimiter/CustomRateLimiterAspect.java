package com.concepts.ratelimiter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Aspect
@Component
public class CustomRateLimiterAspect {

    // Store request timestamps per method
    // Key: Method name, Value: List of timestamps
    private final Map<String, CopyOnWriteArrayList<Long>> requestTimestamps = new ConcurrentHashMap<>();

    @Around("@annotation(customRateLimiter)")
    public Object rateLimit(ProceedingJoinPoint pjp, CustomRateLimiter customRateLimiter) throws Throwable {
        
        String methodName = pjp.getSignature().toShortString();
        int limit = customRateLimiter.limit();
        int windowInSeconds = customRateLimiter.windowInSeconds();
        long windowInMillis = windowInSeconds * 1000L;
        
        System.out.println("[CustomRateLimiter] Checking rate limit for: " + methodName);
        System.out.println("[CustomRateLimiter] Config: limit=" + limit + ", window=" + windowInSeconds + "s");
        
        // Get or create timestamp list for this method
        CopyOnWriteArrayList<Long> timestamps = requestTimestamps.computeIfAbsent(
            methodName, 
            k -> new CopyOnWriteArrayList<>()
        );
        
        long currentTime = System.currentTimeMillis();
        long windowStart = currentTime - windowInMillis;
        
        // Remove timestamps outside the current window (cleanup old entries)
        timestamps.removeIf(timestamp -> timestamp < windowStart);
        
        // Count requests within current window
        long requestsInWindow = timestamps.stream()
            .filter(timestamp -> timestamp >= windowStart)
            .count();
        
        System.out.println("[CustomRateLimiter] Current window: " + requestsInWindow + "/" + limit + " requests");
        
        // Custom rate limiting logic, if request accepted, invoke the method
        if (requestsInWindow < limit) {
            // Add current request timestamp
            timestamps.add(currentTime);
            System.out.println("[CustomRateLimiter] ✓ Request ACCEPTED - proceeding with method execution");
            return pjp.proceed();
        } else {
            System.out.println("[CustomRateLimiter] ✗ Request REJECTED - rate limit exceeded");
            throw new RuntimeException("Rate limit exceeded. Try later!");
        }
    }
}


