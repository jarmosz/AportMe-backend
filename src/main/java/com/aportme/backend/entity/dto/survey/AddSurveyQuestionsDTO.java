package com.aportme.backend.entity.dto.survey;

import lombok.Data;

import java.util.List;

@Data
public class AddSurveyQuestionsDTO {

    List<SurveyQuestionDTO> questions;
}
