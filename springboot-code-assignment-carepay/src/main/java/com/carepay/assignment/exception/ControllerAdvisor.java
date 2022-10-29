package com.carepay.assignment.exception;

import com.carepay.assignment.domain.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<Message> handle(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new Message(HttpStatus.NOT_FOUND.value(), exception.getMessage())
        );
    }

    @ExceptionHandler(value = ServiceValidationException.class )
    protected ResponseEntity<Message> handle(ServiceValidationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new Message(HttpStatus.BAD_REQUEST.value(), exception.getMessage())
        );
    }
}
