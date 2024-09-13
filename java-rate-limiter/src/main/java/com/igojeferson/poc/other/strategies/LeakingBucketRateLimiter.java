package com.igojeferson.poc.other.strategies;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LeakingBucketRateLimiter {

    private final long capacity; // Maximum number of requests that the bucket can hold
    private final long leakIntervalInMillis; // Interval at which the bucket leaks requests
    private final ReentrantLock lock = new ReentrantLock(); // Lock to ensure thread safety
    private long currentWaterLevel; // Current number of requests in the bucket
    private long lastLeakTimestamp; // The last time when the bucket leaked

    public LeakingBucketRateLimiter(long capacity, long leakRate, TimeUnit unit) {
        this.capacity = capacity;
        this.leakIntervalInMillis = unit.toMillis(leakRate);
        this.currentWaterLevel = 0;
        this.lastLeakTimestamp = System.currentTimeMillis();
    }

    // Attempt to add a request to the bucket
    public boolean tryAcquire() {
        lock.lock();
        try {
            leakRequestsIfNecessary();

            if (currentWaterLevel < capacity) {
                currentWaterLevel++; // Add the request to the bucket
                return true;
            } else {
                // Bucket is full, reject the request
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    // Leak requests from the bucket based on the elapsed time
    private void leakRequestsIfNecessary() {
        long now = System.currentTimeMillis();
        long timeSinceLastLeak = now - lastLeakTimestamp;

        if (timeSinceLastLeak > leakIntervalInMillis) {
            long leaks = timeSinceLastLeak / leakIntervalInMillis; // Calculate how many leaks to process
            currentWaterLevel = Math.max(0, currentWaterLevel - leaks); // Decrease the water level
            lastLeakTimestamp = now; // Update the last leak time
        }
    }

}
