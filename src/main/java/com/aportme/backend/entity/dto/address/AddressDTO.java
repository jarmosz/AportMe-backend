package com.aportme.backend.entity.dto.address;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddressDTO extends AddressBaseDTO {

    private Long id;

}
