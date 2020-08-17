package com.aportme.backend.controller;

import com.aportme.backend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExistsException() {
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserIsAlreadyActivatedException.class)
    public ResponseEntity<Object> userIsAlreadyActivatedException() {
        return new ResponseEntity<>("User is already activated", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PetPictureNotFoundException.class)
    public ResponseEntity<Object> petPictureNotFoundException
            () {
        return new ResponseEntity<>("Picture not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongUserCredentialsException.class)
    public ResponseEntity<Object> wrongUserCredentialsException() {
        return new ResponseEntity<>("Wrong credentials", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
