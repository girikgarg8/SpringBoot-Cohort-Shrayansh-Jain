# Retry Pattern Demo

This project demonstrates different retry strategies using Resilience4j in a microservices architecture.

## Architecture

- **Eureka Server**: Service Discovery (Port 8761)
- **Product Service**: Provider service (Port 8082)
- **Order Service**: Consumer service with retry patterns (Port 8081)

---

## Retry Strategies Implemented

### 1. **Fixed Interval Retry**
- **Endpoint**: `GET /orders/retry-fixed/{id}`
- **Configuration**: 
  - Max Attempts: 3
  - Wait Duration: 2 seconds (fixed)
- **Behavior**: Waits exactly 2 seconds between each retry
- **Use Case**: Simple scenarios where constant delay is acceptable

### 2. **Exponential Backoff**
- **Endpoint**: `GET /orders/retry-exponential/{id}`
- **Configuration**:
  - Max Attempts: 4
  - Initial Wait: 1 second
  - Multiplier: 2x
- **Behavior**: 1s → 2s → 4s → 8s
- **Use Case**: Give downstream service time to recover, reduces load

### 3. **Exponential Backoff + Jitter**
- **Endpoint**: `GET /orders/retry-jitter/{id}`
- **Configuration**:
  - Max Attempts: 4
  - Initial Wait: 1 second
  - Multiplier: 2x
  - Randomized Wait: Enabled
- **Behavior**: 1s ± random → 2s ± random → 4s ± random
- **Use Case**: Prevents thundering herd problem when multiple clients retry simultaneously

### 4. **Custom Retry (IntervalFunction)**
- **Endpoint**: `GET /orders/retry-custom/{id}`
- **Configuration**: Defined programmatically in `Config.java`
  - Max Attempts: 4
  - Custom interval function
- **Behavior**: Fully customizable wait times
- **Use Case**: Complex retry logic (Fibonacci, adaptive delays, etc.)

---

## How to Run

### Step 1: Start Eureka Server

```bash
cd /Users/ggarg1/Personal/Springboot-Learning/Second-Iteration-Revision/Retry-Pattern/Eureka-Server
mvn spring-boot:run -s maven-settings.xml
```

Wait for: `Started LearningSpringBootApplication`
Open: http://localhost:8761

### Step 2: Start Product Service

```bash
cd /Users/ggarg1/Personal/Springboot-Learning/Second-Iteration-Revision/Retry-Pattern/Product-Service
mvn spring-boot:run -s maven-settings.xml
```

Wait for: `registration status: 204`

### Step 3: Start Order Service

```bash
cd /Users/ggarg1/Personal/Springboot-Learning/Second-Iteration-Revision/Retry-Pattern/Order-Service
mvn spring-boot:run -s maven-settings.xml
```

Wait for: `Started OrderServiceApplication`

---

## Testing Scenarios

### Scenario 1: Test Fixed Interval Retry

**Setup**: All services running

```bash
# Verify Product Service is up
curl http://localhost:8082/products/123
# Should return: "Product with ID: 123 from Product Service"

# Test with retry (should succeed on first attempt)
curl http://localhost:8081/orders/retry-fixed/123
```

**Now bring down Product Service** (Ctrl+C in Product Service terminal)

```bash
# This will retry 3 times with 2-second delays
curl http://localhost:8081/orders/retry-fixed/123
```

**Watch Order Service logs:**
```
calling product service at 22:35:45.793776
calling product service at 22:35:47.803405  (2sec delay)
calling product service at 22:35:49.823919  (2sec delay)
All retries failed. This is fallback
```

---

### Scenario 2: Test Exponential Backoff

**Bring down Product Service** (if not already)

```bash
curl http://localhost:8081/orders/retry-exponential/456
```

**Watch Order Service logs:**
```
calling product service at 22:37:22.089776  (Original attempt)
calling product service at 22:37:23.096422  (1sec delay)
calling product service at 22:37:25.103589  (2sec delay)
calling product service at 22:37:29.113519  (4sec delay)
All retries failed. This is fallback
```

**Notice**: Delays are increasing exponentially (1s, 2s, 4s)

---

### Scenario 3: Test Exponential Backoff + Jitter

**Bring down Product Service** (if not already)

```bash
curl http://localhost:8081/orders/retry-jitter/789
```

**Watch Order Service logs:**
```
calling product service at 22:38:35.793776
calling product service at 22:38:36.989316  (~1sec + random)
calling product service at 22:38:39.321918  (~2sec + random)
calling product service at 22:38:43.556329  (~4sec + random)
All retries failed. This is fallback
```

**Notice**: Delays are similar to exponential but with slight randomization

---

### Scenario 4: Test Custom Retry

**Bring down Product Service** (if not already)

```bash
curl http://localhost:8081/orders/retry-custom/999
```

**Watch Order Service logs:**
```
calling product service at 22:39:45.793776
calling product service at 22:39:47.803405  (2sec delay)
calling product service at 22:39:49.823919  (2sec delay)
calling product service at 22:39:51.843216  (2sec delay)
All retries failed. This is fallback
```

---

## Comparison Table

| Strategy | Attempts | Delay Pattern | Best For |
|----------|----------|---------------|----------|
| **Fixed Interval** | 3 | 2s, 2s | Simple retries, predictable timing |
| **Exponential Backoff** | 4 | 1s, 2s, 4s, 8s | Give service time to recover |
| **Exponential + Jitter** | 4 | ~1s, ~2s, ~4s | Multiple clients, prevent thundering herd |
| **Custom** | 4 | Fully customizable | Complex business logic |

---

## Key Configuration Files

### application.properties
```properties
# Fixed Interval
resilience4j.retry.instances.productService.maxAttempts=3
resilience4j.retry.instances.productService.waitDuration=2s

# Exponential Backoff
resilience4j.retry.instances.productServiceExponential.maxAttempts=4
resilience4j.retry.instances.productServiceExponential.waitDuration=1s
resilience4j.retry.instances.productServiceExponential.enableExponentialBackoff=true
resilience4j.retry.instances.productServiceExponential.exponentialBackoffMultiplier=2

# Exponential + Jitter
resilience4j.retry.instances.productServiceJitter.enableRandomizedWait=true
```

### Config.java
```java
@Bean
public Retry customRetry() {
    IntervalFunction fibonacciInterval = attempt -> 2000L;
    RetryConfig config = RetryConfig.custom()
            .maxAttempts(4)
            .intervalFunction(fibonacciInterval)
            .retryExceptions(Exception.class)
            .build();
    return Retry.of("customRetry", config);
}
```

---

## Troubleshooting

### Issue: "No servers available for service: product-service"
**Solution**: Make sure Product Service is fully registered with Eureka before starting Order Service
- Check Eureka Dashboard: http://localhost:8761
- Wait for "registration status: 204" in Product Service logs

### Issue: Retries not happening
**Solution**: Make sure Product Service is actually down (stopped)
- Stop Product Service: Ctrl+C in Product Service terminal
- Verify it's down: `curl http://localhost:8082/products/123` should fail

---

## Demo Tips

1. **Start services in correct order**: Eureka → Product → Order
2. **Verify registration**: Check Eureka Dashboard before testing
3. **Watch Order Service logs**: Shows retry attempts with timestamps
4. **Bring up Product Service during retry**: See recovery in action!
5. **Compare retry patterns**: Notice timing differences between strategies

---

## Endpoint Summary

| Endpoint | Retry Strategy | Port |
|----------|---------------|------|
| `/orders/retry-fixed/{id}` | Fixed Interval (2s) | 8081 |
| `/orders/retry-exponential/{id}` | Exponential Backoff | 8081 |
| `/orders/retry-jitter/{id}` | Exponential + Jitter | 8081 |
| `/orders/retry-custom/{id}` | Custom IntervalFunction | 8081 |
| `/products/{id}` | Product endpoint | 8082 |
| Eureka Dashboard | Service Discovery | 8761 |

Happy Testing! 🚀

