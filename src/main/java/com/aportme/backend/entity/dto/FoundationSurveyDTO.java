package com.aportme.backend.entity.dto;

import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.enums.FoundationSurveyStatus;
import lombok.Data;

import java.util.List;

@Data
public class FoundationSurveyDTO {

    private FoundationSurveyStatus surveyStatus;

    private List<SurveyQuestionDTO> activeQuestions;
}
