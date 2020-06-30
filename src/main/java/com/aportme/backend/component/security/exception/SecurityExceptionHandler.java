package com.aportme.backend.component.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException ex){
        return new ResponseEntity<>("User was passed wrong password.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<String> handleWrongPasswordException(UserDoesNotExistsException ex){
        return new ResponseEntity<>("User with given email does not exists.", HttpStatus.NOT_FOUND);
    }
}