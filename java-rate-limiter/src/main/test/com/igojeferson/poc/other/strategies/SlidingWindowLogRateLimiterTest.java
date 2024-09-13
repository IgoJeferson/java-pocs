package com.igojeferson.poc.other.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowLogRateLimiterTest {
    
    private SlidingWindowLogRateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        // Set up a rate limiter that allows 5 requests per second
        rateLimiter = new SlidingWindowLogRateLimiter(5, 1, TimeUnit.SECONDS);
    }

    @Test
    void testBasicFunctionality() {
        // Ensure that the first 5 requests are allowed
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed.");
        }

        // The 6th request should be denied since it exceeds the rate limit
        assertFalse(rateLimiter.tryAcquire(), "6th request should be denied - rate limit exceeded.");
    }

    @Test
    void testSlidingWindowReset() throws InterruptedException {
        // Fill up the allowed request quota
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed.");
        }

        // The 6th request should be denied
        assertFalse(rateLimiter.tryAcquire(), "Request 6 should be denied - rate limit exceeded.");

        // Wait for 1 second (window expiration)
        Thread.sleep(1000);

        // Now, new requests should be allowed as the window has reset
        assertTrue(rateLimiter.tryAcquire(), "Request should be allowed after window reset.");
    }

    @Test
    void testPartialWindowExpiration() throws InterruptedException {
        // Send 3 requests, these should be allowed
        for (int i = 0; i < 3; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed.");
        }

        // Wait for 500 ms, these requests should still be within the window
        Thread.sleep(500);

        // Send 2 more requests, these should also be allowed
        for (int i = 3; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed.");
        }

        // The next request should be denied as it exceeds the limit
        assertFalse(rateLimiter.tryAcquire(), "6th request should be denied - rate limit exceeded.");

        // Wait for another 500 ms, now the original 3 requests should expire
        Thread.sleep(500);

        // Now, new requests should be allowed since the oldest requests have expired
        assertTrue(rateLimiter.tryAcquire(), "Request should be allowed after partial window expiration.");
    }

    @Test
    void testConcurrentRequests() throws InterruptedException {
        // Simulate concurrent access to the rate limiter
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

        // After concurrent access, we expect the rate limiter to have reached its capacity
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket full after concurrent access.");
    }

    @Test
    void testEdgeCaseAtBoundary() throws InterruptedException {
        // Send 5 requests immediately
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed.");
        }

        // Wait just under 1 second
        Thread.sleep(900);

        // Requests should still be denied as the window hasn't fully expired
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - window hasn't reset yet.");

        // Wait for 200 ms more, making the total sleep time 1.1 seconds (so that we're into the next window)
        Thread.sleep(200);

        // Now, the rate limiter should allow new requests
        assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - new window started.");
    }

    @Test
    void testHighTrafficScenario() throws InterruptedException {
        // Simulate rapid requests
        for (int i = 0; i < 100; i++) {
            if (i < 5) {
                assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed.");
            } else {
                assertFalse(rateLimiter.tryAcquire(), "Request " + i + " should be denied - rate limit exceeded.");
            }
        }

        // Wait for 1 second to pass and let the window reset
        Thread.sleep(1000);

        // After window reset, allow another 5 requests
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request " + i + " should be allowed after window reset.");
        }
    }
}
