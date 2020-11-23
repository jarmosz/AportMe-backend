package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.exception.UnableToDeleteNotSubmittedSurveyException;
import com.aportme.backend.repository.survey.UserSurveyRepository;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.PetService;
import com.aportme.backend.service.UserService;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserSurveyService {

    private final UserSurveyRepository userSurveyRepository;
    private final AuthenticationService authenticationService;
    private final SurveyAnswerService surveyAnswerService;
    private final UserService userService;
    private final PetService petService;
    private final ModelMapper modelMapper;

    public List<UserSurveyDTO> getAll() {
        ModelMapperUtil.mapUserSurveyDTO(modelMapper);
        Long id = authenticationService.getLoggedUserId();
        User user = userService.findById(id);

        return userSurveyRepository.findAllByUser(user)
                .stream()
                .map(this::convertToSurveyDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createSurvey(CreateSurveyDTO dto) {
        UserSurvey userSurvey = new UserSurvey();

        Long loggedUserId = authenticationService.getLoggedUserId();
        User user = userService.findById(loggedUserId);
        userSurvey.setUser(user);

        Pet pet = petService.findById(dto.getPetId());
        userSurvey.setPet(pet);
        userSurvey.setFoundation(pet.getFoundation());

        userSurvey = userSurveyRepository.save(userSurvey);
        surveyAnswerService.createSurveyAnswers(userSurvey, pet.getFoundation().getId(), dto.getAnswers());
    }

    public boolean isAnyUserSurveyWithoutDecision(Foundation foundation) {
        return userSurveyRepository.existsByFoundationAndStatus(foundation, SurveyStatus.SUBMITTED);
    }

    @Transactional
    public void delete(Long id) {
        UserSurvey userSurvey = findById(id);
        if (userSurvey.getStatus().equals(SurveyStatus.SUBMITTED)) {
            surveyAnswerService.deleteAllBySurvey(userSurvey);
            userSurveyRepository.delete(userSurvey);
        } else {
            throw new UnableToDeleteNotSubmittedSurveyException();
        }
    }

    public UserSurvey findById(Long id) {
        return userSurveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey not found"));
    }

    private UserSurveyDTO convertToSurveyDTO(UserSurvey userSurvey) {
        UserSurveyDTO dto = modelMapper.map(userSurvey, UserSurveyDTO.class);
        PetPicture profilePicture = userSurvey.getPet().getPictures()
                .stream()
                .filter(PetPicture::getIsProfilePicture)
                .findFirst()
                .orElse(userSurvey.getPet().getPictures().get(0));

        dto.setPicture(profilePicture.getPictureInBase64());
        return dto;
    }
}
