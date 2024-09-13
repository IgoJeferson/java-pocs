package com.igojeferson.poc.other.strategies;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class FixedWindowRateLimiter {
    private final long maxRequests;         // Maximum number of requests allowed in the window
    private final long windowSizeInMillis;  // Duration of the fixed window in milliseconds
    private long currentWindowStartTime;    // Start time of the current window
    private long requestCount;              // Number of requests made in the current window

    private final ReentrantLock lock = new ReentrantLock();  // To handle concurrency

    public FixedWindowRateLimiter(long maxRequests, long windowSize, TimeUnit unit) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = unit.toMillis(windowSize);
        this.currentWindowStartTime = System.currentTimeMillis();
        this.requestCount = 0;
    }

    // Try to acquire permission to make a request
    public boolean tryAcquire() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();

            // If the current time is beyond the current window, reset the counter
            if (now - currentWindowStartTime >= windowSizeInMillis) {
                currentWindowStartTime = now;
                requestCount = 0;
            }

            // If requests are within the limit, allow and increment the count
            if (requestCount < maxRequests) {
                requestCount++;
                return true;
            }

            // Otherwise, deny the request
            return false;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Example usage: Allow up to 5 requests per second
        FixedWindowRateLimiter rateLimiter = new FixedWindowRateLimiter(5, 1, TimeUnit.SECONDS);

        // Simulate 10 requests
        for (int i = 0; i < 10; i++) {
            if (rateLimiter.tryAcquire()) {
                System.out.println("Request " + i + " allowed");
            } else {
                System.out.println("Request " + i + " denied - rate limit exceeded");
            }
            Thread.sleep(200);  // Simulate time between requests
        }
    }
}
