package com.igojeferson.poc.other.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class FixedWindowRateLimiterTest {

    private FixedWindowRateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        // Set up a FixedWindowRateLimiter allowing 5 requests per second
        rateLimiter = new FixedWindowRateLimiter(5, 1, TimeUnit.SECONDS);
    }

    @Test
    void testInitialization() {
        // Ensure that the first 5 requests are allowed within the first window
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - within rate limit.");
        }
        // The 6th request should be denied since it exceeds the rate limit in the same window
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - rate limit exceeded.");
    }

    @Test
    void testRateLimitingWithinWindow() {
        // Fill the bucket completely
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - within rate limit.");
        }

        // The next request should be denied
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - rate limit exceeded.");

        // The rate limiter should still deny requests until the window resets
        assertFalse(rateLimiter.tryAcquire(), "Request should still be denied - rate limit exceeded.");
    }

    @Test
    void testWindowReset() throws InterruptedException {
        // Fill the bucket completely
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - within rate limit.");
        }

        // Wait for the window to reset (1 second)
        Thread.sleep(1000);

        // The new window should allow requests again
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed after window reset.");
        }

        // The 6th request in the new window should be denied
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - rate limit exceeded in new window.");
    }

    @Test
    void testEdgeCaseAtWindowBoundary() throws InterruptedException {
        // Simulate requests near the boundary of the window
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - within rate limit.");
        }

        // Wait just under the window reset threshold
        Thread.sleep(900);  // Slightly less than 1 second to ensure we're still in the same window

        // The request should still be denied as it's within the same window
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - rate limit exceeded, window hasn't reset.");

        // Wait for the window to fully reset
        Thread.sleep(200);  // Wait until we're into the next window

        // Now, the request should be allowed
        assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - new window started.");
    }

    @Test
    void testMultipleWindows() throws InterruptedException {
        // Fill the bucket completely in the first window
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - within rate limit.");
        }

        // Wait for 2 seconds to pass, allowing 2 full windows to reset
        Thread.sleep(2000);

        // The limiter should allow requests in the new window
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - new window.");
        }

        // Ensure the next request in the same window is denied
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - rate limit exceeded in the current window.");
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        // Simulate concurrent requests
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

        // After both threads run, the bucket should be full
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - bucket full after concurrent access.");
    }
}
