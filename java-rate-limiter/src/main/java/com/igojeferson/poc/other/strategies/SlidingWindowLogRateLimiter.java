package com.igojeferson.poc.other.strategies;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindowLogRateLimiter {
    private final long maxRequests;        // Maximum number of requests allowed
    private final long windowSizeInMillis; // Size of the sliding window in milliseconds
    private final Queue<Long> requestTimestamps; // Queue to store timestamps of incoming requests
    private final ReentrantLock lock = new ReentrantLock(); // Lock to ensure thread safety

    public SlidingWindowLogRateLimiter(long maxRequests, long windowSize, TimeUnit unit) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = unit.toMillis(windowSize);
        this.requestTimestamps = new LinkedList<>();
    }

    // Try to acquire permission for a request
    public boolean tryAcquire() {
        lock.lock();
        try {
            long now = System.currentTimeMillis();
            // Remove timestamps that are outside the current window
            while (!requestTimestamps.isEmpty() && now - requestTimestamps.peek() >= windowSizeInMillis) {
                requestTimestamps.poll();
            }

            // Check if we can allow the current request
            if (requestTimestamps.size() < maxRequests) {
                requestTimestamps.offer(now); // Log the current request timestamp
                return true;
            } else {
                // Too many requests in the current window
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // Example: Allow up to 5 requests in a sliding window of 1 second
        SlidingWindowLogRateLimiter rateLimiter = new SlidingWindowLogRateLimiter(5, 1, TimeUnit.SECONDS);

        // Simulate 10 requests
        for (int i = 0; i < 10; i++) {
            if (rateLimiter.tryAcquire()) {
                System.out.println("Request " + i + " allowed");
            } else {
                System.out.println("Request " + i + " denied - rate limit exceeded");
            }
            Thread.sleep(200); // Simulate time between requests
        }
    }
}
