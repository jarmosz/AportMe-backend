package com.aportme.backend.entity.dto.userInfo;

import com.aportme.backend.entity.dto.address.AddOrUpdateAddressDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserInfoDTO extends UserInfoBaseDTO {

    private AddOrUpdateAddressDTO address;
}
