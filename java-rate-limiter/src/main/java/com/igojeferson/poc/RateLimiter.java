package com.igojeferson.poc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private static final long MAX_REQUESTS_PER_WINDOW = 10; // 10 requests allowed per window

    // Stores the count of requests per userId and their last window start time
    private final Map<String, UserRequestInfo> userRequestMap = new ConcurrentHashMap<>();

    // Entry point method to check if a request is allowed
    public boolean isAllowed(String userId) {
        long currentTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

        // Get or create user request information
        userRequestMap.computeIfAbsent(userId, k -> new UserRequestInfo(currentTimeInSeconds, 0));

        UserRequestInfo userInfo = userRequestMap.get(userId);

        synchronized (userInfo) {
            // Check if we're in the same window or a new one
            if (userInfo.windowStartTime == currentTimeInSeconds) {
                // Same window, check request count
                if (userInfo.requestCount < MAX_REQUESTS_PER_WINDOW) {
                    userInfo.requestCount++;
                    return true;
                } else {
                    // Rate limit exceeded
                    return false;
                }
            } else {
                // New window, reset request count
                userInfo.windowStartTime = currentTimeInSeconds;
                userInfo.requestCount = 1;
                return true;
            }
        }
    }



}
