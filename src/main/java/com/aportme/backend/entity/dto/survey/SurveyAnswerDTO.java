package com.aportme.backend.entity.dto.survey;

import lombok.Data;

@Data
public class SurveyAnswerDTO {

    private Long questionId;

    private String answer;
}
