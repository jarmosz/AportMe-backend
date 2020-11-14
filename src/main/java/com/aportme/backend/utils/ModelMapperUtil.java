package com.aportme.backend.utils;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.survey.UserSurveyAnswer;
import com.aportme.backend.entity.dto.survey.UserSurveyDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.survey.Survey;
import com.aportme.backend.entity.survey.SurveyAnswer;
import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    public static void mapFoundationEmail(ModelMapper modelMapper) {
        modelMapper.typeMap(Foundation.class, FoundationDTO.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getUser().getEmail(), FoundationDTO::setEmail)
                );
    }

    public static void mapSurveyDTO(ModelMapper modelMapper) {
        modelMapper.typeMap(Survey.class, UserSurveyDTO.class)
                .addMappings(mapper -> {
                            mapper.map(src -> src.getFoundation().getName(), UserSurveyDTO::setFoundationName);
                            mapper.map(src -> src.getFoundation().getUser().getEmail(), UserSurveyDTO::setFoundationEmail);
                            mapper.map(src -> src.getFoundation().getPhoneNumber(), UserSurveyDTO::setPhoneNumber);
                            mapper.map(src -> src.getPet().getId(), UserSurveyDTO::setPetId);
                            mapper.map(src -> src.getPet().getName(), UserSurveyDTO::setPetName);
                            mapper.map(src -> src.getPet().getBreed(), UserSurveyDTO::setBreed);
                        }
                );

        modelMapper.typeMap(SurveyAnswer.class, UserSurveyAnswer.class)
                .addMappings(mapper -> mapper.map(src -> src.getQuestion().getQuestionText(), UserSurveyAnswer::setQuestionText));
    }
}
