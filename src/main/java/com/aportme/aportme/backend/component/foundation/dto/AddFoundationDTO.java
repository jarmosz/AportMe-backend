package com.aportme.aportme.backend.component.foundation.dto;

import com.aportme.aportme.backend.component.address.dto.AddOrUpdateAddressDTO;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class AddFoundationDTO implements DTOEntity {

    private String name;

    private String nip;

    private String phoneNumber;

    private AddOrUpdateAddressDTO address;

}
