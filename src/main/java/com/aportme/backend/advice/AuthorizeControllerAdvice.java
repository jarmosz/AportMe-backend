package com.aportme.backend.advice;

import com.aportme.backend.exception.*;
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

    @ExceptionHandler(WrongChangePasswordDataException.class)
    public ResponseEntity<Object> wrongChangePasswordDataException() {
        return new ResponseEntity<>("You have provided wrong user password or invalidate new password data", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongLikeRequestDataException.class)
    public ResponseEntity<Object> wrongLikeRequestDataException() {
        return new ResponseEntity<>("You have provided wrong param request data", HttpStatus.CONFLICT);
    }
}
