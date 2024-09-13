package com.igojeferson.poc.other.strategies;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TokenBucketRateLimiterTest {
    private TokenBucketRateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        // Set up a rate limiter with 5 tokens max, refilling 1 token every second.
        rateLimiter = new TokenBucketRateLimiter(5, 1, 1, TimeUnit.SECONDS);
    }

    @Test
    void testInitialization() {
        // After initialization, we should have 5 tokens available.
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - token available.");
        }
        // The 6th request should be denied, as we only initialized with 5 tokens.
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - no tokens available.");
    }

    @Test
    void testTokenConsumption() {
        // Consume all tokens
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - token available.");
        }
        // Now the bucket should be empty
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - no tokens available.");
    }

    @Test
    void testRateLimiting() throws InterruptedException {
        // Consume all tokens
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - token available.");
        }

        // Wait less than the refill interval (1 second)
        Thread.sleep(500);
        // No tokens should have been refilled yet
        assertFalse(rateLimiter.tryAcquire(), "Request should still be denied - not enough time has passed for refill.");

        // Wait for refill interval to pass
        Thread.sleep(600);
        // Now one token should have been refilled
        assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - token has been refilled.");
    }

    @Test
    void testMultipleRefillIntervals() throws InterruptedException {
        // Consume all tokens
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - token available.");
        }

        // Wait for 3 refill intervals (3 seconds)
        Thread.sleep(3000);

        // 3 tokens should have been refilled, allowing 3 more requests
        for (int i = 0; i < 3; i++) {
            assertTrue(rateLimiter.tryAcquire(), "Request should be allowed - tokens have been refilled.");
        }

        // The 4th request should be denied, as only 3 tokens were refilled
        assertFalse(rateLimiter.tryAcquire(), "Request should be denied - no more tokens available.");
    }

    @Test
    void testConcurrentRequests() throws InterruptedException {
        // A simple test to ensure we can handle concurrent requests.
        // We simulate this by using multiple threads.
        Runnable task = () -> {
            for (int i = 0; i < 2; i++) {
                if (rateLimiter.tryAcquire()) {
                    System.out.println("Request allowed");
                } else {
                    System.out.println("Request denied");
                }
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
