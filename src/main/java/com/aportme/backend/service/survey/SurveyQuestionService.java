package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.survey.CreateSurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.repository.survey.SurveyQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyQuestionService {

    private final SurveyQuestionRepository surveyQuestionRepository;
    private final ModelMapper modelMapper;

    public SurveyQuestion create(CreateSurveyQuestionDTO dto, Foundation foundation) {
        SurveyQuestion question = mapToSurveyQuestion(dto);
        question.setFoundation(foundation);
        question.setFoundationSurvey(foundation.getFoundationSurvey());
        return question;
    }

    public void delete(SurveyQuestion question) {
        surveyQuestionRepository.delete(question);
    }

    public List<SurveyQuestion> findAllActiveQuestionByFoundation(Foundation foundation) {
        return surveyQuestionRepository.findAllByFoundationAndQuestionStatus(foundation, QuestionStatus.ACTIVE);
    }

    public SurveyQuestion save(SurveyQuestion surveyQuestion) {
        return surveyQuestionRepository.save(surveyQuestion);
    }

    public SurveyQuestion findById(Long id) {
        return surveyQuestionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey question not found"));
    }

    public SurveyQuestionDTO mapToSurveyQuestionDTO(SurveyQuestion question) {
        return modelMapper.map(question, SurveyQuestionDTO.class);
    }

    public SurveyQuestion mapToSurveyQuestion(CreateSurveyQuestionDTO dto) {
        return modelMapper.map(dto, SurveyQuestion.class);
    }

    public List<SurveyQuestionDTO> mapToSurveyQuestionDTO(List<SurveyQuestion> questions) {
        return questions
                .stream()
                .map(question -> modelMapper.map(question, SurveyQuestionDTO.class))
                .collect(Collectors.toList());
    }
}
