package com.aportme.backend.entity.dto.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class AddressBaseDTO {

    private String city;

    private String street;

    private String houseNumber;

    private String flatNumber;

    private String zipCode;
}
