package com.aportme.backend.advice;

import com.aportme.backend.exception.InvalidSurveyQuestionException;
import com.aportme.backend.exception.ResetPasswordLinkTokenNotFound;
import com.aportme.backend.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class NotFoundControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundException() {
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundException(Exception exception) {
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidSurveyQuestionException.class)
    public ResponseEntity<Object> invalidSurveyQuestion(InvalidSurveyQuestionException ex) {
        String msg = String.format("Unable to create survey invalid question id: %d", ex.getId());
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResetPasswordLinkTokenNotFound.class)
    public ResponseEntity<Object> resetPasswordTokenNotFoundException() {
        return new ResponseEntity<>("Reset password token not found", HttpStatus.NOT_FOUND);
    }

}
