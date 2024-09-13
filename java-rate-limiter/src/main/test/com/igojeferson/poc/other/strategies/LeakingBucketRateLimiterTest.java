package com.igojeferson.poc.other.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class LeakingBucketRateLimiterTest {
    private LeakingBucketRateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        // Set up a leaking bucket rate limiter with a capacity of 5 and leak 1 request per second
        rateLimiter = new LeakingBucketRateLimiter(5, 1, TimeUnit.SECONDS);
    }

    @Test
    void testInitialization() {
        // Test that the first 5 requests are allowed as the bucket is initially empty
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - bucket is not full.");
        }

        // The 6th request should be denied as the bucket is full
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket is full.");
    }

    @Test
    void testRequestRejection() {
        // Fill the bucket completely
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - bucket is not full.");
        }

        // The next request should be denied as the bucket is full
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket overflow.");
    }

    @Test
    void testLeakingBehavior() throws InterruptedException {
        // Fill the bucket
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - bucket is not full.");
        }

        // The 6th request should be denied
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket overflow.");

        // Wait for 1.5 seconds, enough time for 1 request to leak out
        Thread.sleep(1500);

        // Now a request should be allowed again
        assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - bucket leaked 1 request.");

        // The next request should still be denied since we only leaked 1
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket is full.");
    }

    @Test
    void testMultipleLeaks() throws InterruptedException {
        // Fill the bucket
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - bucket is not full.");
        }

        // Wait for 3 seconds (enough time for 3 requests to leak out)
        Thread.sleep(3000);

        // Now, 3 more requests should be allowed
        for (int i = 0; i < 3; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - bucket leaked 3 requests.");
        }

        // The next request should be denied since only 3 requests leaked out
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket is full.");
    }

    @Test
    void testConcurrentRequests() throws InterruptedException {
        // Create two threads simulating concurrent access to the rate limiter
        Runnable task = () -> {
            for (int i = 0; i < 3; i++) {
                if (rateLimiter.tryAcquire()) {
                    System.out.println("Request allowed by " + Thread.currentThread().getName());
                } else {
                    System.out.println("Request denied by " + Thread.currentThread().getName());
                }
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        // After the concurrent requests, the bucket should be full
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket should be full after concurrent access.");
    }
}
