package com.aportme.backend.entity.dto.survey;

import com.aportme.backend.entity.enums.FoundationSurveyStatus;
import lombok.Data;

import java.util.List;

@Data
public class FoundationSurveyDTO {

    private FoundationSurveyStatus surveyStatus;

    private List<SurveyQuestionDTO> activeQuestions;
}
