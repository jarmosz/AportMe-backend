package com.aportme.backend.component.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WrongUserCredentialsExceptionController {

    @ExceptionHandler(value = WrongUserCredentialsException.class)
    public ResponseEntity<Object> exception(WrongUserCredentialsException exception) {
        return new ResponseEntity<>("Email has wrong format or password is shorter than 9 characters", HttpStatus.BAD_REQUEST);
    }
}
