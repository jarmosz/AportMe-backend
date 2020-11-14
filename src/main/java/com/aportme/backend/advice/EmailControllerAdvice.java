package com.aportme.backend.advice;

import com.aportme.backend.exception.EmailProblemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmailControllerAdvice {
    @ExceptionHandler(EmailProblemException.class)
    public ResponseEntity<Object> emailProblemException() {
        return new ResponseEntity<>("Connection problem with email server or bad email data.", HttpStatus.SERVICE_UNAVAILABLE);
    }
}
