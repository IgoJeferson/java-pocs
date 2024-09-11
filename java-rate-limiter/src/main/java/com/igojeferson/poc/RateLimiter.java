package com.igojeferson.poc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {
    
    private final int MAX_REQUESTS_PER_SECOND = 10;
    private final long ONE_SECOND_IN_MILLIS = 1000;

    // Map to track the requests per user
    private ConcurrentHashMap<String, List<Long>> userRequestMap;

    public RateLimiter() {
        this.userRequestMap = new ConcurrentHashMap<>();
    }

    // Entry point for rate limiting check
    public boolean isAllowed(String userId) {
        long currentTime = System.currentTimeMillis();

        // Get the user's request history, or create a new one if it doesn't exist
        userRequestMap.putIfAbsent(userId, new ArrayList<>());
        List<Long> requestTimestamps = userRequestMap.get(userId);

        // Remove expired timestamps that are outside of the 1-second window
        requestTimestamps.removeIf(timestamp -> (currentTime - timestamp) >= ONE_SECOND_IN_MILLIS);

        // Check if the number of requests in the current window exceeds the limit
        if (requestTimestamps.size() < MAX_REQUESTS_PER_SECOND) {
            // Add the current request's timestamp
            requestTimestamps.add(currentTime);
            return true;
        } else {
            return false; // Deny request if over the limit
        }
    }
}
