package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.PetPicture;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.repository.survey.UserSurveyRepository;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
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

    public Page<UserSurvey> findAllByPet(Pageable pageable, Pet pet) {
        return userSurveyRepository.findAllByPet(pageable, pet);
    }

    public Page<UserSurvey> findAllByFoundation(Pageable pageable, Foundation foundation) {
        return userSurveyRepository.findAllByFoundation(pageable, foundation);
    }

    public UserSurvey save(UserSurvey survey) {
        return userSurveyRepository.save(survey);
    }

    public void delete(UserSurvey survey) {
        userSurveyRepository.delete(survey);
    }

    public List<UserSurveyDTO> mapToSurveyDTO(List<UserSurvey> surveys) {
        ModelMapperUtil.mapUserSurveyDTO(modelMapper);
        return surveys
                .stream()
                .map(this::convertToSurveyDTO)
                .collect(Collectors.toList());
    }

    public UserSurveyDTO mapToSurveyDTO(UserSurvey userSurvey) {
        ModelMapperUtil.mapUserSurveyDTO(modelMapper);
        return modelMapper.map(userSurvey, UserSurveyDTO.class);
    }

    private UserSurveyDTO convertToSurveyDTO(UserSurvey userSurvey) {
        UserSurveyDTO dto = mapToSurveyDTO(userSurvey);
        PetPicture profilePicture = userSurvey.getPet().getPictures()
                .stream()
                .filter(PetPicture::getIsProfilePicture)
                .findFirst()
                .orElse(userSurvey.getPet().getPictures().get(0));

        dto.setPicture(profilePicture.getPictureInBase64());
        return dto;
    }
}
