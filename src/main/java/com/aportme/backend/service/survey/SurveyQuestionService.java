package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.dto.survey.CreateSurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.enums.QuestionType;
import com.aportme.backend.entity.survey.SelectValue;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.repository.survey.SurveyQuestionRepository;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.service.PetService;
import com.aportme.backend.service.SelectValueService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyQuestionService {

    private final SurveyQuestionRepository surveyQuestionRepository;
    private final FoundationService foundationService;
    private final SelectValueService selectValueService;
    private final PetService petService;
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;
    private SurveyAnswerService surveyAnswerService;

    public List<SurveyQuestionDTO> getQuestions(Long petId) {
        Pet pet = petService.findById(petId);
        Foundation foundation = pet.getFoundation();

        return findAllActiveQuestionByFoundation(foundation)
                .stream()
                .map(question -> modelMapper.map(question, SurveyQuestionDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public SurveyQuestionDTO createQuestions(CreateSurveyQuestionDTO question) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);
        return saveSurveyQuestion(foundation, question);
    }

    @Transactional
    public void deleteAll() {
        String foundationEmail = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(foundationEmail);
        List<SurveyQuestion> questions = findAllActiveQuestionByFoundation(foundation);

        questions.forEach(this::deprecateQuestionOrDelete);
    }

    @Transactional
    public void deleteById(Long id) {
        SurveyQuestion question = findById(id);
        deprecateQuestionOrDelete(question);
    }

    public SurveyQuestion findById(Long id) {
        return surveyQuestionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Survey question not found"));
    }

    private void deprecateQuestionOrDelete(SurveyQuestion question) {
        boolean isAnswerPresent = surveyAnswerService.isAtLeastOneAnswerToQuestion(question);

        if (isAnswerPresent) {
            question.setQuestionStatus(QuestionStatus.DEPRECATED);
            save(question);
        } else {
            if (question.getType().equals(QuestionType.SELECT)) {
                selectValueService.deleteAllByQuestion(question);
            }
            surveyQuestionRepository.delete(question);
        }
    }

    public List<SurveyQuestion> findAllActiveQuestionByFoundation(Foundation foundation) {
        return surveyQuestionRepository.findAllByFoundationAndQuestionStatus(foundation, QuestionStatus.ACTIVE);
    }

    private SurveyQuestionDTO saveSurveyQuestion(Foundation foundation, CreateSurveyQuestionDTO questionDTO) {
        SurveyQuestion question = modelMapper.map(questionDTO, SurveyQuestion.class);
        question.setFoundation(foundation);
        question.setFoundationSurvey(foundation.getFoundationSurvey());
        question = save(question);
        if (questionDTO.getType().equals(QuestionType.SELECT)) {
            List<SelectValue> values = selectValueService.save(question, questionDTO.getValues());
            question.setSelectValues(values);
        }
        return modelMapper.map(question, SurveyQuestionDTO.class);
    }

    private SurveyQuestion save(SurveyQuestion surveyQuestion) {
        return surveyQuestionRepository.save(surveyQuestion);
    }

    @Autowired
    public void setSurveyAnswerService(SurveyAnswerService service) {
        this.surveyAnswerService = service;
    }
}
