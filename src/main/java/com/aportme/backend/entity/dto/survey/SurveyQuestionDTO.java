package com.aportme.backend.entity.dto.survey;

import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.enums.QuestionType;
import lombok.Data;

import java.util.List;

@Data
public class SurveyQuestionDTO {

    private Long id;

    private String questionText;

    private QuestionType type;

    private QuestionStatus questionStatus;

    private List<SelectValueDTO> selectValues;
}
