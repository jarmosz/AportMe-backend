package com.aportme.backend.entity.dto.survey;

import com.aportme.backend.entity.enums.SurveyStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompletedSurveyDTO {

    private Long id;

    private SurveyStatus status;

    private Long petId;

    private String petName;

    private String userEmail;

    private LocalDateTime createdAt;

    private List<UserSurveyAnswerDTO> answers;
}
