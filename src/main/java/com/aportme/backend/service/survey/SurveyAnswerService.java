package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.survey.SurveyAnswerDTO;
import com.aportme.backend.entity.survey.Survey;
import com.aportme.backend.entity.survey.SurveyAnswer;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.exception.InvalidSurveyQuestionException;
import com.aportme.backend.repository.survey.SurveyAnswerRepository;
import com.aportme.backend.service.FoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyAnswerService {

    private final SurveyAnswerRepository surveyAnswerRepository;
    private final FoundationService foundationService;
    private SurveyQuestionService surveyQuestionService;

    public void deleteAllByQuestion(SurveyQuestion question) {
        List<SurveyAnswer> answers = surveyAnswerRepository.findAllByQuestion(question);
        surveyAnswerRepository.deleteAll(answers);
    }

    public void createSurveyAnswers(Survey survey, Long foundationId, List<SurveyAnswerDTO> userAnswers) {
        Foundation foundation = foundationService.findById(foundationId);
        List<SurveyQuestion> questions = surveyQuestionService.findAllByFoundation(foundation);

        List<SurveyAnswer> answers = userAnswers.stream()
                .map(answer -> createSurveyAnswer(survey, questions, answer))
                .collect(Collectors.toList());

        surveyAnswerRepository.saveAll(answers);
    }

    private SurveyAnswer createSurveyAnswer(Survey survey, List<SurveyQuestion> questions, SurveyAnswerDTO dto) {
        SurveyQuestion question = findQuestion(questions, dto.getId());
        return SurveyAnswer.builder()
                .answer(dto.getAnswer())
                .question(question)
                .survey(survey)
                .build();
    }

    private SurveyQuestion findQuestion(List<SurveyQuestion> questions, Long questionId) {
        return questions
                .stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new InvalidSurveyQuestionException(questionId));
    }

    @Autowired
    public void setSurveyQuestionService(SurveyQuestionService service) {
        this.surveyQuestionService = service;
    }
}
