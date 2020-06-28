package com.aportme.backend.component.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserIsAlreadyActivatedExceptionController {

    @ExceptionHandler(value = UserIsAlreadyActivatedException.class)
    public ResponseEntity<Object> exception(UserIsAlreadyActivatedException exception) {
        return new ResponseEntity<>("User account is already activated", HttpStatus.CONFLICT);
    }
}
