package com.carepay.assignment.exception;

public class NotFoundException extends RuntimeException {

    private Object[] messageParams;

    public NotFoundException(String message) {
        super(message);
    }

}
