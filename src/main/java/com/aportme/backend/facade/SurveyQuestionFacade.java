package com.aportme.backend.facade;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.CreateSurveyQuestionDTO;
import com.aportme.backend.entity.dto.survey.SurveyQuestionDTO;
import com.aportme.backend.entity.enums.QuestionStatus;
import com.aportme.backend.entity.enums.QuestionType;
import com.aportme.backend.entity.survey.SelectValue;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.exception.FoundationSurveyInactiveException;
import com.aportme.backend.exception.UserSurveyAlreadyExistsException;
import com.aportme.backend.service.*;
import com.aportme.backend.service.survey.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class SurveyQuestionFacade {

    private final AuthenticationService authenticationService;
    private final SurveyQuestionService questionService;
    private final UserSurveyService surveyService;
    private final FoundationService foundationService;
    private final SurveyAnswerService surveyAnswerService;
    private final SelectValueService selectValueService;
    private final FoundationSurveyService foundationSurveyService;
    private final UserService userService;
    private final PetService petService;

    public List<SurveyQuestionDTO> getQuestions(Long petId) {
        Pet pet = petService.findById(petId);
        Foundation foundation = pet.getFoundation();

        if (foundationSurveyService.isFoundationSurveyActive(foundation)) {
            throwExceptionIfUserSurveyAlreadySubmitted(pet);

            List<SurveyQuestion> questions = questionService.findAllActiveQuestionByFoundation(foundation);
            return questionService.mapToSurveyQuestionDTO(questions);
        }

        throw new FoundationSurveyInactiveException();
    }

    public SurveyQuestionDTO createQuestion(CreateSurveyQuestionDTO dto) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);

        SurveyQuestion question = questionService.create(dto, foundation);
        question = questionService.save(question);
        if (dto.getType() == QuestionType.SELECT) {
            List<SelectValue> values = selectValueService.convertAndSave(question, dto.getValues());
            question.setSelectValues(values);
        }

        return questionService.mapToSurveyQuestionDTO(question);
    }

    public void deleteById(Long id) {
        SurveyQuestion question = questionService.findById(id);
        deprecateQuestionOrDelete(question);
    }

    public void deleteAll() {
        String foundationEmail = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(foundationEmail);
        List<SurveyQuestion> questions = questionService.findAllActiveQuestionByFoundation(foundation);

        questions.forEach(this::deprecateQuestionOrDelete);
    }

    private void deprecateQuestionOrDelete(SurveyQuestion question) {
        boolean isAnswerPresent = surveyAnswerService.isAtLeastOneAnswerToQuestion(question);

        if (isAnswerPresent) {
            question.setQuestionStatus(QuestionStatus.DEPRECATED);
        } else {
            if (question.getType() == QuestionType.SELECT) {
                selectValueService.deleteAllByQuestion(question);
            }
            questionService.delete(question);
        }
    }

    private void throwExceptionIfUserSurveyAlreadySubmitted(Pet pet) {
        User user = userService.getLoggedUser();
        UserSurvey survey = surveyService.findByUserAndPetOrNull(user, pet);

        if(survey != null) {
            throw new UserSurveyAlreadyExistsException();
        }
    }
}
