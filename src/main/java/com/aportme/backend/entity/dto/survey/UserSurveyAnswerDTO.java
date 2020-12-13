package com.aportme.backend.entity.dto.survey;

import lombok.Data;

@Data
public class UserSurveyAnswerDTO {

    private String questionText;

    private String answer;
}
