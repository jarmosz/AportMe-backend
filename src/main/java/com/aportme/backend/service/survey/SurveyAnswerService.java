package com.aportme.backend.service.survey;

import com.aportme.backend.entity.survey.SurveyAnswer;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.repository.survey.SurveyAnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SurveyAnswerService {

    private final SurveyAnswerRepository surveyAnswerRepository;

    public void deleteAllByQuestion(SurveyQuestion question) {
        List<SurveyAnswer> answers = surveyAnswerRepository.findAllByQuestion(question);
        surveyAnswerRepository.deleteAll(answers);
    }
}
