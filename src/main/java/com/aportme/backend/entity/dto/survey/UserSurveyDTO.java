package com.aportme.backend.entity.dto.survey;

import com.aportme.backend.entity.enums.SurveyStatus;
import lombok.Data;

import javax.persistence.Lob;
import java.util.List;

@Data
public class UserSurveyDTO {

    private Long id;

    private SurveyStatus status;

    private String foundationName;

    private String foundationEmail;

    private String phoneNumber;

    private Long petId;

    private String petName;

    private String breed;

    private String dowloadUrl;

    private List<UserSurveyAnswerDTO> answers;
}
