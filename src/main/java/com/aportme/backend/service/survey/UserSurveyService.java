package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.repository.survey.UserSurveyRepository;
import com.aportme.backend.service.PictureService;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSurveyService {

    private final UserSurveyRepository userSurveyRepository;
    private final ModelMapper modelMapper;

    public boolean isAnyUserSurveyWithoutDecision(Foundation foundation) {
        return userSurveyRepository.existsByFoundationAndStatus(foundation, SurveyStatus.SUBMITTED);
    }

    public UserSurvey findById(Long id) {
        return userSurveyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey not found"));
    }

    public UserSurvey findByUserAndPetOrNull(User user, Pet pet) {
        return userSurveyRepository.findByUserAndPet(user, pet).orElse(null);
    }

    public List<UserSurvey> findAllByUser(User user) {
        return userSurveyRepository.findAllByUser(user);
    }

    public Page<UserSurvey> findAllByPetName(Pageable pageable, String petName) {
        return userSurveyRepository.findAllByPet_SearchableNameContains(pageable, petName.toLowerCase());
    }

    public UserSurvey save(UserSurvey survey) {
        return userSurveyRepository.save(survey);
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
