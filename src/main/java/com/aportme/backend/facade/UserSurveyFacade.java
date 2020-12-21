package com.aportme.backend.facade;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.SurveyQuestion;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.exception.UnableToDeleteNotSubmittedSurveyException;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.PetService;
import com.aportme.backend.service.PictureService;
import com.aportme.backend.service.UserService;
import com.aportme.backend.service.survey.SurveyAnswerService;
import com.aportme.backend.service.survey.SurveyQuestionService;
import com.aportme.backend.service.survey.UserSurveyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    private final PictureService pictureService;

    public List<UserSurveyDTO> getAll() {
        Long id = authenticationService.getLoggedUserId();
        User user = userService.findById(id);

        List<UserSurvey> surveys = userSurveyService.findAllByUser(user);
        return convertToUserSurveyDTO(surveys);
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

    public List<UserSurveyDTO> convertToUserSurveyDTO(List<UserSurvey> surveys) {
        return surveys
                .stream()
                .map(survey -> {
                    List<PetPicture> pictures = survey.getPet().getPictures();
                    PetPicture profilePicture = pictureService.findProfilePicture(pictures);
                    return userSurveyService.mapToSurveyDTO(survey, profilePicture);
                })
                .collect(Collectors.toList());
    }

}
