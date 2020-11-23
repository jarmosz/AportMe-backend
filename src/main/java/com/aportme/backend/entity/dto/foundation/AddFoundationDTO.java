package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddressBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AddFoundationDTO extends FoundationBaseDTO {

    private String accountNumber;

    private AddressBaseDTO address;
}
