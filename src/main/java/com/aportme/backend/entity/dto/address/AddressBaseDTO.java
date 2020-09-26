package com.aportme.backend.entity.dto.address;

import lombok.Data;

@Data
class AddressBaseDTO {

    private String city;

    private String street;

    private String houseNumber;

    private String flatNumber;

    private String zipCode;
}
