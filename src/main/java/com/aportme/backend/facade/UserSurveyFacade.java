package com.aportme.backend.facade;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.exception.UnableToDeleteNotSubmittedSurveyException;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.PetService;
import com.aportme.backend.service.UserService;
import com.aportme.backend.service.survey.SurveyAnswerService;
import com.aportme.backend.service.survey.SurveyQuestionService;
import com.aportme.backend.service.survey.UserSurveyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
@AllArgsConstructor
public class UserSurveyFacade {

    private final UserSurveyService userSurveyService;
    private final AuthenticationService authenticationService;
    private final SurveyAnswerService surveyAnswerService;
    private final PetService petService;
    private final SurveyQuestionService questionService;
    private final UserService userService;

    public List<UserSurveyDTO> getAll() {
        Long id = authenticationService.getLoggedUserId();
        User user = userService.findById(id);

        List<UserSurvey> surveys = userSurveyService.findAllByUser(user);
        return userSurveyService.mapToSurveyDTO(surveys);
    }

    public void createSurvey(CreateSurveyDTO dto) {
        UserSurvey userSurvey = new UserSurvey();

        Long loggedUserId = authenticationService.getLoggedUserId();
        User user = userService.findById(loggedUserId);
        userSurvey.setUser(user);

        Pet pet = petService.findById(dto.getPetId());
        Foundation foundation = pet.getFoundation();
        userSurvey.setPet(pet);
        userSurvey.setFoundation(foundation);

        userSurvey = userSurveyService.save(userSurvey);
        List<SurveyQuestion> questions = questionService.findAllActiveQuestionByFoundation(foundation);
        surveyAnswerService.createSurveyAnswers(userSurvey, questions, dto.getAnswers());
    }

    public void delete(Long id) {
        UserSurvey userSurvey = userSurveyService.findById(id);
        if (userSurvey.getStatus() == SurveyStatus.SUBMITTED) {
            surveyAnswerService.deleteAllBySurvey(userSurvey);
            userSurveyService.delete(userSurvey);
        } else {
            throw new UnableToDeleteNotSubmittedSurveyException();
        }
    }
}
