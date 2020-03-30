package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.UserInfoDTO;
import com.aportme.aportme.backend.entity.user.User;
import com.aportme.aportme.backend.entity.user.UserInfo;
import com.aportme.aportme.backend.repository.UserInfoRepository;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.utils.EntityDTOConverter;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;
    private final EntityDTOConverter entityDTOConverter;

    public DTOEntity getById(Long id) {
        Optional<UserInfo> userInfoFromDB = userInfoRepository.findById(id);
        if (userInfoFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(userInfoFromDB.get(), new UserInfoDTO());
    }

    public DTOEntity update(Long id, UserInfoDTO userInfoDTO) throws Exception {
        Optional<UserInfo> userInfoFromDB = userInfoRepository.findById(id);
        if (userInfoFromDB.isEmpty()) {
            throw new Exception("UserInfo not found");
        }
        UserInfo userInfo = userInfoFromDB.get();
        UtilsService.copyNonNullProperties(userInfoDTO, userInfo);
        return entityDTOConverter.convertToDto(userInfoRepository.save(userInfo), new UserInfoDTO());
    }

    public DTOEntity create(Long userId, UserInfoDTO userInfoDTO) throws Exception {
        UserInfo dbUserInfo = new UserInfo();
        dbUserInfo.setPhoneNumber(userInfoDTO.getPhoneNumber());
        dbUserInfo.setName(userInfoDTO.getName());
        dbUserInfo.setSurname(userInfoDTO.getSurname());
        dbUserInfo.setAddress(userInfoDTO.getAddress());
        Optional<User> userFromDB = userRepository.findById(userId);
        if (userFromDB.isEmpty()) {
            throw new Exception("User not found");
        }
        dbUserInfo.setUser(userFromDB.get());
        return entityDTOConverter.convertToDto(userInfoRepository.save(dbUserInfo), new UserInfoDTO());
    }

}
