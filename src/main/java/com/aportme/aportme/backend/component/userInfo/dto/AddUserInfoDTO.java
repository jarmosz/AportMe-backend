package com.aportme.aportme.backend.component.userInfo.dto;

import com.aportme.aportme.backend.component.address.dto.AddOrUpdateAddressDTO;
import lombok.Data;

@Data
public class AddUserInfoDTO {

    private String name;

    private String surname;

    private String phoneNumber;

    private AddOrUpdateAddressDTO address;
}
