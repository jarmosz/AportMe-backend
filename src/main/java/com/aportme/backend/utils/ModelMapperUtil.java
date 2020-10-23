package com.aportme.backend.utils;

import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.dto.foundation.FoundationDTO;
import org.modelmapper.ModelMapper;

public class ModelMapperUtil {

    public static void mapUserToFoundationDTO(ModelMapper modelMapper) {
        modelMapper.typeMap(Foundation.class, FoundationDTO.class)
                .addMappings(mapper -> mapper.map(src -> src.getUser().getEmail(), FoundationDTO::setEmail));
    }
}
