package com.aportme.backend.entity.dto;

import com.aportme.backend.entity.enums.SurveyStatus;
import lombok.Data;

import java.util.List;

@Data
public class SurveyDTO {

    private Long id;

    private SurveyStatus status;

    private String foundationName;

    private String foundationEmail;

    private String phoneNumber;

    private Long petId;

    private String petName;

    private String breed;

    private String picture;

    private List<SurveyAnswerDTO> answers;
}
