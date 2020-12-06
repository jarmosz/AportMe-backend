package com.aportme.backend.service.survey;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.UpdateSurveyStatusDTO;
import com.aportme.backend.entity.dto.UserSurveyForFoundationDTO;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.service.AuthenticationService;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserSurveyForFoundationService {

    private final ModelMapper modelMapper;
    private final UserSurveyService surveyService;
    private final AuthenticationService authenticationService;
    private final FoundationService foundationService;

    public Page<UserSurveyForFoundationDTO> getSurveysForFoundation(Pageable pageable, String search) {
        Page<UserSurvey> page;
        if (search != null) {
            page = surveyService.findAllByPetName(pageable, search);
        } else {
            page = getAllUserSurveysForFoundation(pageable);
        }

        List<UserSurveyForFoundationDTO> surveys = convertToUserSurveysForFoundationDTO(page);
        return new PageImpl<>(surveys, pageable, page.getTotalElements());
    }

    private List<UserSurveyForFoundationDTO> convertToUserSurveysForFoundationDTO(Page<UserSurvey> page) {
        return page
                .getContent()
                .stream()
                .map(this::mapToUserSurveyFoundationDTO)
                .collect(Collectors.toList());
    }

    private UserSurveyForFoundationDTO mapToUserSurveyFoundationDTO(UserSurvey userSurvey) {
        ModelMapperUtil.mapUserSurveyForFoundationDTO(modelMapper);
        return modelMapper.map(userSurvey, UserSurveyForFoundationDTO.class);
    }

    private Page<UserSurvey> getAllUserSurveysForFoundation(Pageable pageable) {
        String email = authenticationService.getLoggedUsername();
        Foundation foundation = foundationService.findByEmail(email);

        return surveyService.findAllByFoundation(pageable, foundation);
    }

    public UserSurveyForFoundationDTO changeStatus(UpdateSurveyStatusDTO dto) {
        UserSurvey survey = surveyService.findById(dto.getSurveyId());
        survey.setStatus(dto.getStatus());
        survey = surveyService.save(survey);
        return mapToUserSurveyFoundationDTO(survey);
    }
}
