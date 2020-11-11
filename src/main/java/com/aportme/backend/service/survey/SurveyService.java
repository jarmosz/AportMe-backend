package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.CreateSurveyDTO;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.survey.Survey;
import com.aportme.backend.repository.survey.SurveyRepository;
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
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final AuthenticationService authenticationService;
    private final SurveyAnswerService surveyAnswerService;
    private final UserService userService;
    private final PetService petService;
    private final ModelMapper modelMapper;

    public List<UserSurveyDTO> getAll() {
        ModelMapperUtil.mapSurveyDTO(modelMapper);
        Long id = authenticationService.getLoggedUserId();
        User user = userService.findById(id);

        return surveyRepository.findAllByUser(user)
                .stream()
                .map(this::convertToSurveyDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createSurvey(CreateSurveyDTO dto) {
        Survey survey = new Survey();

        Long loggedUserId = authenticationService.getLoggedUserId();
        User user = userService.findById(loggedUserId);
        survey.setUser(user);

        Pet pet = petService.findById(dto.getPetId());
        survey.setPet(pet);
        survey.setFoundation(pet.getFoundation());

        survey = surveyRepository.save(survey);
        surveyAnswerService.createSurveyAnswers(survey, pet.getFoundation().getId(), dto.getAnswers());
    }
    
    @Transactional
    public void delete(Long id) {
        Survey survey = findById(id);
        surveyAnswerService.deleteAllBySurvey(survey);
        surveyRepository.delete(survey);
    }

    public Survey findById(Long id) {
        return surveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey not found"));
    }

    private UserSurveyDTO convertToSurveyDTO(Survey survey) {
        UserSurveyDTO dto = modelMapper.map(survey, UserSurveyDTO.class);
        PetPicture profilePicture = survey.getPet().getPictures()
                .stream()
                .filter(PetPicture::getIsProfilePicture)
                .findFirst()
                .orElse(survey.getPet().getPictures().get(0));

        dto.setPicture(profilePicture.getPictureInBase64());
        return dto;
    }
}
