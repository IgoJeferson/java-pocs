package com.igojeferson.poc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RateLimiterTest {
    
    private RateLimiter rateLimiter;

    @BeforeEach
    public void setup() {
        rateLimiter = new RateLimiter();
    }

    @Test
    public void testAllow10RequestsInWindow() throws InterruptedException {
        String userId = "user1";

        // Send 10 requests in the window, all should be allowed
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.isAllowed(userId));
        }
    }

    @Test
    public void testDenyRequestsOverLimit() throws InterruptedException {
        String userId = "user2";

        // Send 20 requests in the window, 10 should be denied
        for (int i = 0; i < 10; i++) {
            assertTrue(rateLimiter.isAllowed(userId));
        }
        for (int i = 0; i < 10; i++) {
            assertFalse(rateLimiter.isAllowed(userId));
        }
    }

    @Test
    public void testFastAndSlowUsers() throws InterruptedException {
        String fastUser = "fastUser";
        String slowUser = "slowUser";

        // Simulate a fast user sending 20 requests, 10 should be denied
        for (int i = 0; i < 20; i++) {
            if (i < 10) {
                assertTrue(rateLimiter.isAllowed(fastUser));
            } else {
                assertFalse(rateLimiter.isAllowed(fastUser));
            }
        }

        // Simulate a slow user sending 5 requests, all should be allowed
        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.isAllowed(slowUser));
            Thread.sleep(200); // Delay for slow user
        }
    }
}
