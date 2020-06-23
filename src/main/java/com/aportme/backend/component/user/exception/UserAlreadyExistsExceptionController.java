package com.aportme.backend.component.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserAlreadyExistsExceptionController {

        @ExceptionHandler(value = UserAlreadyExistsException.class)
        public ResponseEntity<Object> exception(UserAlreadyExistsException exception) {
            return new ResponseEntity<>("User with this email already exists in the database", HttpStatus.CONFLICT);
        }
}

