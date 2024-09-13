package com.igojeferson.poc;

// Helper class to track user request data
public class UserRequestInfo {
        long windowStartTime;
        long requestCount;

        UserRequestInfo(long windowStartTime, long requestCount) {
            this.windowStartTime = windowStartTime;
            this.requestCount = requestCount;
        }
    }