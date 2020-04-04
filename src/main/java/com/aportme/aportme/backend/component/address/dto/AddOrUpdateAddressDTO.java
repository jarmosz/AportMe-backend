package com.aportme.aportme.backend.component.address.dto;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class AddOrUpdateAddressDTO implements DTOEntity {

    private String city;

    private String street;

    private String houseNumber;

    private String flatNumber;

    private String zipCode;
}
