package com.aportme.backend.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateSurveyDTO {

    private Long petId;

    private List<SurveyAnswerDTO> answers;
}
