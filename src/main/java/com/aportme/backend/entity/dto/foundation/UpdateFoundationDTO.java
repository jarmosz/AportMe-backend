package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddressBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateFoundationDTO extends FoundationBaseDTO {

    private String accountNumber;

    private String foundationLogo;

    private AddressBaseDTO address;
}
