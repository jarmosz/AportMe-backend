package com.aportme.backend.entity.dto.survey;

import com.aportme.backend.entity.enums.QuestionType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SurveyQuestionDTO {

    private String questionText;

    private QuestionType type;

    private List<String> values = new ArrayList<>();
}
