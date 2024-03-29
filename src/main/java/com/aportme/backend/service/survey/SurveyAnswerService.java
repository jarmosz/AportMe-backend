package com.aportme.backend.service.survey;

import com.aportme.backend.entity.dto.survey.SurveyAnswerDTO;
import com.aportme.backend.entity.survey.SurveyAnswer;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.exception.InvalidSurveyQuestionException;
import com.aportme.backend.repository.survey.SurveyAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyAnswerService {

    private final SurveyAnswerRepository surveyAnswerRepository;

    public void createSurveyAnswers(UserSurvey userSurvey, List<SurveyQuestion> questions, List<SurveyAnswerDTO> userAnswers) {
        List<SurveyAnswer> answers = userAnswers.stream()
                .map(answer -> createSurveyAnswer(userSurvey, questions, answer))
                .collect(Collectors.toList());

        surveyAnswerRepository.saveAll(answers);
    }

    private SurveyAnswer createSurveyAnswer(UserSurvey userSurvey, List<SurveyQuestion> questions, SurveyAnswerDTO dto) {
        SurveyQuestion question = findQuestion(questions, dto.getQuestionId());
        return SurveyAnswer.builder()
                .answer(dto.getAnswer())
                .question(question)
                .userSurvey(userSurvey)
                .build();
    }

    private SurveyQuestion findQuestion(List<SurveyQuestion> questions, Long questionId) {
        return questions
                .stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new InvalidSurveyQuestionException(questionId));
    }

    public boolean isAtLeastOneAnswerToQuestion(SurveyQuestion question) {
        return surveyAnswerRepository.existsByQuestion(question);
    }

    public void deleteAllBySurvey(UserSurvey userSurvey) {
        surveyAnswerRepository.deleteAllByUserSurvey(userSurvey);
    }
}
