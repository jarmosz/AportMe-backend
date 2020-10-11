package com.aportme.backend.entity.dto.pet;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePetDTO extends PetBaseDTO {

    private Long id;
}
