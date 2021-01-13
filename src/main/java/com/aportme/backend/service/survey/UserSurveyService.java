package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.repository.survey.UserSurveyRepository;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSurveyService {

    private final UserSurveyRepository userSurveyRepository;
    private final SurveyAnswerService surveyAnswerService;
    private final ModelMapper modelMapper;

    public UserSurvey findById(Long id) {
        return userSurveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey not found"));
    }

    public UserSurvey findByUserAndPetOrNull(User user, Pet pet) {
        return userSurveyRepository.findByUserAndPet(user, pet).orElse(null);
    }

    public List<UserSurvey> findAllByUser(User user) {
        return userSurveyRepository.findAllByUser(user);
    }

    public Page<UserSurvey> findAllByPetNameAndFoundation(Pageable pageable, Foundation foundation, String petName) {
        return userSurveyRepository.findAllByFoundationAndPet_SearchableNameContainingIgnoreCase(pageable, foundation, petName.toLowerCase());
    }

    public UserSurvey save(UserSurvey survey) {
        return userSurveyRepository.save(survey);
    }

    public void deleteAllByPet(Pet pet) {
        List<UserSurvey> surveys = userSurveyRepository.findAllByPet(pet);
        surveys.forEach(surveyAnswerService::deleteAllBySurvey);
        userSurveyRepository.deleteAll(surveys);
    }

    public void delete(UserSurvey survey) {
        userSurveyRepository.delete(survey);
    }

    public UserSurveyDTO mapToSurveyDTO(UserSurvey userSurvey, PetPicture profilePicture) {
        ModelMapperUtil.mapUserSurveyDTO(modelMapper);
        UserSurveyDTO dto = modelMapper.map(userSurvey, UserSurveyDTO.class);
        dto.setPicture(profilePicture.getPictureInBase64());
        return dto;
    }
}
