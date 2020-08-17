package com.aportme.backend.service;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.User;
import com.aportme.backend.exception.UserNotFoundException;
import com.aportme.backend.entity.dto.userInfo.AddUserInfoDTO;
import com.aportme.backend.entity.dto.userInfo.UpdateUserInfoDTO;
import com.aportme.backend.entity.dto.userInfo.UserInfoDTO;
import com.aportme.backend.entity.UserInfo;
import com.aportme.backend.repository.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final AddressService addressService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserInfoDTO getById(Long id) {
        UserInfo user = findById(id);
        return modelMapper.map(user, UserInfoDTO.class);
    }

    public ResponseEntity<Object> update(Long id, UpdateUserInfoDTO userInfoDTO) {
        UserInfo user = findById(id);
        modelMapper.map(userInfoDTO, user);
        userInfoRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> create(Long userId, AddUserInfoDTO userInfoDTO) {
        User user = userService.findUserById(userId);
        UserInfo userInfo = modelMapper.map(userInfoDTO, UserInfo.class);
        Address address = addressService.createAddress(userInfoDTO.getAddress());

        userInfo.setUser(user);
        userInfo.setAddress(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private UserInfo findById(Long id) {
        return userInfoRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
