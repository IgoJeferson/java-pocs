package com.igojeferson.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateLimiterTest {

    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        rateLimiter = new RateLimiter();
    }

    @Test
    void testTenRequestsAllowed() {
        // Send 10 requests within the same 1 second window
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.isAllowed("user1"), "Request " + i + " should be allowed.");
        }

        // All 10 should be allowed, and the next one should be denied
        assertFalse(rateLimiter.isAllowed("user1"), "Request 11 should be denied - rate limit exceeded.");
    }

    @Test
    void testTwentyRequestsHalfDenied() {
        // Send 10 requests within the same 1 second window
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.isAllowed("user1"), "Request " + i + " should be allowed.");
        }

        // The next 10 requests should be denied
        for (int i = 0; i < 10; i++) {
            assertFalse(rateLimiter.isAllowed("user1"), "Request " + (i + 11) + " should be denied.");
        }
    }

    @Test
    void testConcurrentFastAndSlowUser() throws InterruptedException {
        // Simulate fast and slow users
        Runnable fastUserTask = () -> {
            for (int i = 0; i < 20; i++) {
                if (rateLimiter.isAllowed("fastUser")) {
                    System.out.println("fastUser request " + i + " allowed");
                } else {
                    System.out.println("fastUser request " + i + " denied");
                }
                try {
                    Thread.sleep(50); // Fast user sends requests every 50ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Runnable slowUserTask = () -> {
            for (int i = 0; i < 5; i++) {
                if (rateLimiter.isAllowed("slowUser")) {
                    System.out.println("slowUser request " + i + " allowed");
                } else {
                    System.out.println("slowUser request " + i + " denied");
                }
                try {
                    Thread.sleep(300); // Slow user sends requests every 300ms
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Run both users concurrently
        Thread fastUserThread = new Thread(fastUserTask);
        Thread slowUserThread = new Thread(slowUserTask);

        fastUserThread.start();
        slowUserThread.start();

        fastUserThread.join();
        slowUserThread.join();

        // Ensure that fast user had denials after 10 requests
        assertFalse(rateLimiter.isAllowed("fastUser"), "fastUser should be denied after 10 requests in 1 second.");

        // Ensure that slow user never reached the limit
        assertTrue(rateLimiter.isAllowed("slowUser"), "slowUser should still be allowed.");
    }

    @Test
    void testWindowResetsAfterOneSecond() throws InterruptedException {
        // Send 10 requests, all should be allowed
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.isAllowed("user1"), "Request " + i + " should be allowed.");
        }

        // Sleep for 1 second to reset the window
        Thread.sleep(1000);

        // Now the next 10 requests should be allowed again
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.isAllowed("user1"), "Request " + i + " should be allowed after window reset.");
        }
    }
}
