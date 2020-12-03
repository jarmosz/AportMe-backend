package com.aportme.backend.entity.dto;

import com.aportme.backend.entity.enums.SurveyStatus;
import lombok.Data;

@Data
public class UpdateSurveyStatusDTO {

    private Long surveyId;

    private SurveyStatus status;
}
