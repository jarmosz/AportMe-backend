package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.Pet;
import com.aportme.backend.entity.dto.survey.UpdateSurveyStatusDTO;
import com.aportme.backend.entity.dto.survey.CompletedSurveyDTO;
import com.aportme.backend.entity.enums.AdoptionStatus;
import com.aportme.backend.entity.enums.SurveyStatus;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.service.PaginationService;
import com.aportme.backend.service.PetService;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompletedSurveyService {

    private final ModelMapper modelMapper;
    private final FoundationService foundationService;
    private final AuthenticationService authenticationService;
    private final UserSurveyService surveyService;
    private final PetService petService;

    public Page<CompletedSurveyDTO> getCompletedSurveys(Pageable pageable, String search) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);
        Page<UserSurvey> page = surveyService.findAllByPetNameAndFoundation(pageable, foundation, search);

        List<CompletedSurveyDTO> surveys = convertToCompletedSurveyDTO(page);
        return PaginationService.mapToPageImpl(surveys, pageable, page.getTotalElements());
    }

    private List<CompletedSurveyDTO> convertToCompletedSurveyDTO(Page<UserSurvey> page) {
        return page
                .getContent()
                .stream()
                .map(this::mapToCompletedSurveyDTO)
                .collect(Collectors.toList());
    }

    private CompletedSurveyDTO mapToCompletedSurveyDTO(UserSurvey userSurvey) {
        ModelMapperUtil.mapToCompletedSurveyDTO(modelMapper);
        return modelMapper.map(userSurvey, CompletedSurveyDTO.class);
    }

    public CompletedSurveyDTO changeStatus(UpdateSurveyStatusDTO dto) {
        UserSurvey survey = surveyService.findById(dto.getSurveyId());
        if(dto.getStatus() == SurveyStatus.ACCEPTED) {
            Pet pet = survey.getPet();
            pet.setAdoptionStatus(AdoptionStatus.ADOPTED);
            petService.save(pet);
        }
        survey.setStatus(dto.getStatus());
        survey = surveyService.save(survey);
        return mapToCompletedSurveyDTO(survey);
    }
}
