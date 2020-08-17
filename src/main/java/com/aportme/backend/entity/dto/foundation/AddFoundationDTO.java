package com.aportme.backend.entity.dto.foundation;

import com.aportme.backend.entity.dto.address.AddOrUpdateAddressDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFoundationDTO extends FoundationBaseDTO {

    private String accountNumber;

    private AddOrUpdateAddressDTO address;

}
