package com.igojeferson.poc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter {
    private final long maxTokens;
    private final long refillIntervalInMillis;
    private final long refillTokens;
    private long currentTokens;
    private long lastRefillTimestamp;
    private final ReentrantLock lock = new ReentrantLock();

    public TokenBucketRateLimiter(long maxTokens, long refillTokens, long refillInterval, TimeUnit unit) {
        this.maxTokens = maxTokens;
        this.refillTokens = refillTokens;
        this.refillIntervalInMillis = unit.toMillis(refillInterval);
        this.currentTokens = maxTokens;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    // Try to acquire a token
    public boolean tryAcquire() {
        lock.lock();
        try {
            refillTokensIfNecessary();
            if (currentTokens > 0) {
                currentTokens--;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    // Refill tokens based on the elapsed time
    private void refillTokensIfNecessary() {
        long now = System.currentTimeMillis();
        long timeSinceLastRefill = now - lastRefillTimestamp;

        if (timeSinceLastRefill > refillIntervalInMillis) {
            long tokensToAdd = (timeSinceLastRefill / refillIntervalInMillis) * refillTokens;
            currentTokens = Math.min(maxTokens, currentTokens + tokensToAdd);
            lastRefillTimestamp = now;
        }
    }

}
