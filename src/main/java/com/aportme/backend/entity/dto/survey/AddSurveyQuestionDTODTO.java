package com.aportme.backend.entity.dto.survey;

import lombok.Data;

import java.util.List;

@Data
public class AddSurveyQuestionDTODTO {

    List<AddSurveyQuestionDTO> questions;
}
