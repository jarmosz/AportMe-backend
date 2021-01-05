package com.aportme.backend.advice;

import com.aportme.backend.exception.FoundationSurveyInactiveException;
import com.aportme.backend.exception.UserSurveyWithoutDecisionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SurveysControllerAdvice {

    @ExceptionHandler(UserSurveyWithoutDecisionException.class)
    public ResponseEntity<Object> unresolvedSurvey() {
        return new ResponseEntity<>("You have unresolved surveys", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FoundationSurveyInactiveException.class)
    public ResponseEntity<Object> foundationSurveyInactive() {
//        Flutter rzuca ExceptionFormat jeżeli idzie tutaj wiadomość nie mam pojęcia dlaczego
//        return new ResponseEntity<>("Foundation survey is inactive unable to get questions", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
