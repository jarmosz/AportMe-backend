package com.aportme.aportme.backend.dto.user.userInfo;

import com.aportme.aportme.backend.dto.address.AddOrUpdateAddressDTO;
import lombok.Data;

@Data
public class AddUserDTO {

    private String name;

    private String surname;

    private String phoneNumber;

    private AddOrUpdateAddressDTO address;
}
