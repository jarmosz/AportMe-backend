package com.aportme.aportme.backend.dto.foundation;

import com.aportme.aportme.backend.dto.address.AddOrUpdateAddressDTO;
import com.aportme.aportme.backend.dto.DTOEntity;
import lombok.Data;

@Data
public class AddFoundationDTO implements DTOEntity {

    private String name;

    private String nip;

    private String phoneNumber;

    private AddOrUpdateAddressDTO address;

}
