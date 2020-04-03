package com.aportme.aportme.backend.utils.dto;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EntityDTOConverter {

    private final ModelMapper modelMapper;

    public DTOEntity convertToDto(Object entity, DTOEntity mapper) {
        return modelMapper.map(entity, mapper.getClass());
    }

    public Object convertToEntity(Object object, DTOEntity mapper) {
        return modelMapper.map(mapper, object.getClass());
    }
}
