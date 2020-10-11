package com.aportme.backend.entity.dto.foundation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateFoundationDTO extends FoundationBaseDTO {

    private String accountNumber;
}
