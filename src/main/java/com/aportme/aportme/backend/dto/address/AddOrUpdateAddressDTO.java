package com.aportme.aportme.backend.dto.address;

import com.aportme.aportme.backend.dto.DTOEntity;
import lombok.Data;

@Data
public class AddOrUpdateAddressDTO implements DTOEntity {

    private String city;

    private String street;

    private String houseNumber;

    private String flatNumber;

    private String zipCode;
}
