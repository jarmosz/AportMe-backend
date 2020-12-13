package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddressDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoggedFoundationDataDTO extends FoundationBaseDTO {

    private Long id;

    private String foundationLogo;

    private String accountNumber;

    private String email;

    private AddressDTO address;
}
