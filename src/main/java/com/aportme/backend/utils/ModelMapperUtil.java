package com.aportme.backend.utils;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.SurveyDTO;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import com.aportme.backend.entity.survey.Survey;
import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    public static void mapFoundationEmail(ModelMapper modelMapper) {
        modelMapper.typeMap(Foundation.class, FoundationDTO.class)
                .addMappings(mapper ->
                        mapper.map(src -> src.getUser().getEmail(), FoundationDTO::setEmail)
                );
    }

    public static void mapSurveyDTO(ModelMapper modelMapper) {
        modelMapper.typeMap(Survey.class, SurveyDTO.class)
                .addMappings(mapper -> {
                            mapper.map(src -> src.getFoundation().getName(), SurveyDTO::setFoundationName);
                            mapper.map(src -> src.getFoundation().getUser().getEmail(), SurveyDTO::setFoundationEmail);
                            mapper.map(src -> src.getFoundation().getPhoneNumber(), SurveyDTO::setPhoneNumber);
                            mapper.map(src -> src.getPet().getId(), SurveyDTO::setPetId);
                            mapper.map(src -> src.getPet().getName(), SurveyDTO::setPetName);
                            mapper.map(src -> src.getPet().getBreed(), SurveyDTO::setBreed);
                        }
                );
    }
}
