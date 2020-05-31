package com.aportme.backend.component.userInfo.service;

import com.aportme.backend.component.address.service.AddressService;
import com.aportme.backend.component.userInfo.entity.UserInfo;
import com.aportme.backend.component.userInfo.repository.UserInfoRepository;
import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.component.userInfo.dto.AddUserInfoDTO;
import com.aportme.backend.component.userInfo.dto.UpdateUserInfoDTO;
import com.aportme.backend.component.userInfo.dto.UserInfoDTO;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.repository.UserRepository;
import com.aportme.backend.utils.dto.EntityDTOConverter;
import com.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final EntityDTOConverter entityDTOConverter;
    private final AddressService addressService;

    public DTOEntity getById(Long id) {
        Optional<UserInfo> userInfoFromDB = userInfoRepository.findById(id);
        if (userInfoFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(userInfoFromDB.get(), new UserInfoDTO());
    }

    public DTOEntity update(Long id, UpdateUserInfoDTO userInfoDTO) throws Exception {
        Optional<UserInfo> userInfoFromDB = userInfoRepository.findById(id);
        if (userInfoFromDB.isEmpty()) {
            throw new Exception("UserInfo not found");
        }
        UserInfo dbUserInfo = userInfoFromDB.get();
        UserInfo convertedEntity = (UserInfo) entityDTOConverter.convertToEntity(new UserInfo(), userInfoDTO);
        UtilsService.copyNonNullProperties(convertedEntity, dbUserInfo);
        return entityDTOConverter.convertToDto(userInfoRepository.save(dbUserInfo), new UserInfoDTO());
    }

    public DTOEntity create(Long userId, AddUserInfoDTO userInfoDTO) throws Exception {
        UserInfo dbUserInfo = new UserInfo();
        dbUserInfo.setPhoneNumber(userInfoDTO.getPhoneNumber());
        dbUserInfo.setName(userInfoDTO.getName());
        dbUserInfo.setSurname(userInfoDTO.getSurname());

        Optional<User> userFromDB = userRepository.findById(userId);
        if (userFromDB.isEmpty()) {
            throw new Exception("User not found");
        }

        dbUserInfo.setUser(userFromDB.get());
        dbUserInfo.setAddress(addressService.create(userInfoDTO.getAddress()));
        return entityDTOConverter.convertToDto(userInfoRepository.save(dbUserInfo), new UserInfoDTO());
    }

}
