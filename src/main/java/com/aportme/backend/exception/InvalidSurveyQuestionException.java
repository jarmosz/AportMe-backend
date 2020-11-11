package com.aportme.backend.exception;

import lombok.Getter;

@Getter
public class InvalidSurveyQuestionException extends RuntimeException {

    private final Long id;

    public InvalidSurveyQuestionException(Long id) {
        super();
        this.id = id;
    }
}
