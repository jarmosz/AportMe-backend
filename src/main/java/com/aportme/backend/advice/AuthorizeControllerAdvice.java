package com.aportme.backend.advice;

import com.aportme.backend.exception.UserAlreadyExistsException;
import com.aportme.backend.exception.UserIsAlreadyActivatedException;
import com.aportme.backend.exception.WrongUserCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizeControllerAdvice {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExistsException() {
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIsAlreadyActivatedException.class)
    public ResponseEntity<Object> userIsAlreadyActivatedException() {
        return new ResponseEntity<>("User is already activated", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongUserCredentialsException.class)
    public ResponseEntity<Object> wrongUserCredentialsException() {
        return new ResponseEntity<>("Wrong credentials", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
