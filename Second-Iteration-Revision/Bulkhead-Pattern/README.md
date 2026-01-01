# Bulkhead Pattern with ThreadPool Implementation

This project demonstrates the **Bulkhead Pattern** using **Resilience4j ThreadPool Bulkhead** to limit concurrent calls and prevent cascading failures in a microservices architecture.

## Architecture

- **Eureka Server** (Port 8761): Service Discovery
- **Product Service** (Port 8082): Provides product information (with 2-second delay)
- **Order Service** (Port 8081): Calls Product Service with Bulkhead protection

## Bulkhead Configuration

The Order Service is configured with ThreadPool-based Bulkhead:

```properties
resilience4j.thread-pool-bulkhead.instances.productService.coreThreadPoolSize=3
resilience4j.thread-pool-bulkhead.instances.productService.maxThreadPoolSize=3
resilience4j.thread-pool-bulkhead.instances.productService.queueCapacity=2
```

- **coreThreadPoolSize=3**: 3 threads always available in the pool
- **maxThreadPoolSize=3**: Maximum 3 threads can be created
- **queueCapacity=2**: If all 3 threads are busy, 2 more requests can wait in queue

## How It Works

1. When Order Service receives a request, it calls Product Service through Feign Client
2. The `@Bulkhead` annotation with THREADPOOL type submits the method to a dedicated thread pool
3. **Requests 1-3**: Execute immediately using the 3 available threads
4. **Requests 4-5**: Wait in queue (queue capacity = 2)
5. **Request 6+**: Rejected immediately and trigger fallback (pool full + queue full)
6. As threads complete their work, queued requests are picked up and executed

## Testing Steps

### 1. Start Eureka Server
```bash
cd Eureka-Server
mvn clean install
mvn spring-boot:run
```
Verify at: http://localhost:8761

### 2. Start Product Service
```bash
cd Product-Service
mvn clean install
mvn spring-boot:run
```

### 3. Start Order Service
```bash
cd Order-Service
mvn clean install
mvn spring-boot:run
```

### 4. Test Bulkhead Pattern

**Test with loop (5 concurrent requests):**
```bash
for i in {1..5}; do
  curl -X GET "http://localhost:8081/orders/$i" &
done
wait
```

**Expected Behavior:**
- Requests 1-3: Execute immediately (using 3 threads)
- Requests 4-5: Wait in queue
- After first requests complete, queued requests execute
- Check Order Service console for thread names like "bulkhead-productService-1", "bulkhead-productService-2", "bulkhead-productService-3"

### 5. Observe Results

**Order Service Console Output:**
```
Request 1 received on thread: http-nio-8081-exec-1
Thread name is:bulkhead-productService-1
Response from Product api call is: Product details for ID: 1

Request 2 received on thread: http-nio-8081-exec-2
Thread name is:bulkhead-productService-2
Response from Product api call is: Product details for ID: 2

Request 3 received on thread: http-nio-8081-exec-3
Thread name is:bulkhead-productService-3
Response from Product api call is: Product details for ID: 3

(Requests 4-5 wait in queue, then execute when threads become free)
Thread name is:bulkhead-productService-1
Response from Product api call is: Product details for ID: 4
```

**Product Service Console Output:**
```
Product API called for ID: 1
Product API called for ID: 2
Product API called for ID: 3
(After 2 seconds, queued requests execute)
Product API called for ID: 4
Product API called for ID: 5
```

## Key Concepts

### ThreadPool Bulkhead
- **Dedicated Thread Pool**: Uses separate thread pool for isolation
- **Queue Capacity**: Allows requests to wait when all threads are busy
- **Async Execution**: Returns CompletableFuture for non-blocking responses
- **Resource Isolation**: Each service call uses isolated thread pool

### Benefits
- Prevents resource exhaustion
- Isolates failures
- Maintains service stability under high load
- Fast failure for better user experience

## Additional Testing

Test with different delays:
```bash
# Test with 10 requests
for i in {1..10}; do
  curl -X GET "http://localhost:8081/orders/$i" &
done
wait

# Test with sequential requests (should all succeed)
for i in {1..5}; do
  curl -X GET "http://localhost:8081/orders/$i"
  sleep 3
done
```

## Configuration Variations

Try adjusting the configuration in `Order-Service/application.properties`:

```properties
# Allow 5 threads
resilience4j.thread-pool-bulkhead.instances.productService.coreThreadPoolSize=5
resilience4j.thread-pool-bulkhead.instances.productService.maxThreadPoolSize=5

# Increase queue capacity to 5
resilience4j.thread-pool-bulkhead.instances.productService.queueCapacity=5
```

