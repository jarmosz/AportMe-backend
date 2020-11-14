package com.aportme.backend.advice;

import com.aportme.backend.exception.ResetPasswordLinkTokenHasExpired;
import com.aportme.backend.exception.WrongResetPasswordDataException;
import com.aportme.backend.exception.security.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SecurityControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> handleWrongPasswordException(){
        return new ResponseEntity<>("Wrong password.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserDoesNotExistsException.class)
    public ResponseEntity<String> handleUserDoesNotExistsException(){
        return new ResponseEntity<>("User with given email does not exists.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenDoesNotExsistsException.class)
    public ResponseEntity<String> handleTokenDoesNotExistsException(){
        return new ResponseEntity<>("Token doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(){
        return new ResponseEntity<>("Token is invalid.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<String> handleInvalidTokenTypeException(){
        return new ResponseEntity<>("Token type is invalid.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenHasExpiredException.class)
    public ResponseEntity<String> handleRefreshTokenHasExpiredException(){
        return new ResponseEntity<>("Token has expired.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResetPasswordLinkTokenHasExpired.class)
    public ResponseEntity<String> handleResetPasswordTokenExpiration(){
        return new ResponseEntity<>("Reset password link has expired", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongResetPasswordDataException.class)
    public ResponseEntity<String> handleWrongResetPasswordTokenExpiration(){
        return new ResponseEntity<>("Passwords do not match or have invalid format", HttpStatus.BAD_REQUEST);
    }
}
