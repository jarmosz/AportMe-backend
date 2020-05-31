package com.aportme.backend.component.address.dto;

import com.aportme.backend.utils.dto.DTOEntity;
import lombok.Data;

@Data
public class AddressDTO implements DTOEntity {

    private Long id;

    private String city;

    private String street;

    private String houseNumber;

    private String flatNumber;

    private String zipCode;
}
