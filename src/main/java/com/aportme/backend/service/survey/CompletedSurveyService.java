package com.aportme.backend.service.survey;

import com.aportme.backend.entity.dto.survey.UpdateSurveyStatusDTO;
import com.aportme.backend.entity.dto.survey.CompletedSurveyDTO;
import com.aportme.backend.entity.survey.UserSurvey;
import com.aportme.backend.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompletedSurveyService {

    private final ModelMapper modelMapper;
    private final UserSurveyService surveyService;

    public Page<CompletedSurveyDTO> getCompletedSurveys(Pageable pageable, String search) {
        Page<UserSurvey> page;
        if (search == null) {
            search = "";
        }
        page = surveyService.findAllByPetName(pageable, search);

        List<CompletedSurveyDTO> surveys = convertToCompletedSurveyDTO(page);
        return new PageImpl<>(surveys, pageable, page.getTotalElements());
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
        survey.setStatus(dto.getStatus());
        survey = surveyService.save(survey);
        return mapToCompletedSurveyDTO(survey);
    }
}
