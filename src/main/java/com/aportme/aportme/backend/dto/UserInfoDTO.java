package com.aportme.aportme.backend.dto;

import com.aportme.aportme.backend.entity.Address;
import com.aportme.aportme.backend.entity.user.User;
import lombok.Data;

@Data
public class UserInfoDTO implements DTOEntity {

    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

    private Address address;

    private User user;
}
