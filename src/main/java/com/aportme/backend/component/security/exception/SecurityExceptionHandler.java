package com.aportme.backend.component.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> handleWrongPasswordException(WrongPasswordException ex){
        return new ResponseEntity<>("Wrong password.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<String> handleUserDoesNotExistsException(UserDoesNotExistsException ex){
        return new ResponseEntity<>("User with given email does not exists.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenDoesNotExsistsException.class)
    public ResponseEntity<String> handleTokenDoesNotExistsException(TokenDoesNotExsistsException ex){
        return new ResponseEntity<>("Token doesn't exist.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException ex){
        return new ResponseEntity<>("Token is invalid.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<String> handleInvalidTokenTypeException(InvalidTokenTypeException ex){
        return new ResponseEntity<>("Token type is invalid.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenHasExpiredException.class)
    public ResponseEntity<String> handleRefreshTokenHasExpiredException(RefreshTokenHasExpiredException ex){
        return new ResponseEntity<>("Token has expired.", HttpStatus.UNAUTHORIZED);
    }
}
