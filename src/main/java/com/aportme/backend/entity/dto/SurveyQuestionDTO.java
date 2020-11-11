package com.aportme.backend.entity.dto;

import com.aportme.backend.entity.dto.survey.SelectValueDTO;
import com.aportme.backend.entity.enums.QuestionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class SurveyQuestionDTO {

    private Long id;

    private String questionText;

    private QuestionType type;

    private List<SelectValueDTO> selectValues;
}
