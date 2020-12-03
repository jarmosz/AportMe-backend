package com.aportme.backend.entity.dto;

import com.aportme.backend.entity.dto.survey.UserSurveyAnswerDTO;
import com.aportme.backend.entity.enums.SurveyStatus;
import lombok.Data;

import java.util.List;

@Data
public class UserSurveyForFoundationDTO {

    private Long id;

    private SurveyStatus status;

    private Long petId;

    private String petName;

    private String userEmail;

    private List<UserSurveyAnswerDTO> answers;
}
