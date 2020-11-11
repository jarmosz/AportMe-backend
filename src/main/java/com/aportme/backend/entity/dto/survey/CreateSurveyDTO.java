package com.aportme.backend.entity.dto.survey;

import lombok.Data;

import java.util.List;

@Data
public class CreateSurveyDTO {

    private Long petId;

    private List<SurveyAnswerDTO> answers;
}
