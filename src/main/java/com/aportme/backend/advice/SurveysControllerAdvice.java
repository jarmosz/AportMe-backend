package com.aportme.backend.advice;

import com.aportme.backend.exception.FoundationSurveyInactiveException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SurveysControllerAdvice {

    @ExceptionHandler(FoundationSurveyInactiveException.class)
    public ResponseEntity<Object> foundationSurveyInactive() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
