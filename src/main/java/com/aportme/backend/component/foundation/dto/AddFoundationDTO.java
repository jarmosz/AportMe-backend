package com.aportme.backend.component.foundation.dto;

import com.aportme.backend.component.address.dto.AddOrUpdateAddressDTO;
import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class AddFoundationDTO implements DTOEntity {

    private String name;

    private String nip;

    private String krs;

    private String accountNumber;

    private String phoneNumber;

    private String description;

    private AddOrUpdateAddressDTO address;

}
