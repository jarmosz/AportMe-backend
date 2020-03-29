package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.entity.user.UserInfo;
import com.aportme.aportme.backend.repository.UserInfoRepository;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.utils.UtilsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final UserRepository userRepository;

    public UserInfo getById(Long id) {
        return  userInfoRepository.findById(id).orElse(null);
    }

    public UserInfo update(Long id, UserInfo userInfo) {
        UserInfo dbUserInfo = userInfoRepository.findById(id).get();
        UtilsService.copyNonNullProperties(userInfo, dbUserInfo);
        return userInfoRepository.save(dbUserInfo);
    }

    public UserInfo create(Long userId, UserInfo userInfo) {
        UserInfo dbUserInfo = new UserInfo();
        dbUserInfo.setPhoneNumber(userInfo.getPhoneNumber());
        dbUserInfo.setName(userInfo.getName());
        dbUserInfo.setSurname(userInfo.getSurname());
        dbUserInfo.setAddress(userInfo.getAddress());
        dbUserInfo.setUser(userRepository.findById(userId).get());
        return  userInfoRepository.save(dbUserInfo);
    }

}
