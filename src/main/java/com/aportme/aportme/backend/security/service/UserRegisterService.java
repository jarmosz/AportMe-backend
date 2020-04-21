package com.aportme.aportme.backend.security.service;

import com.aportme.aportme.backend.component.address.dto.AddOrUpdateAddressDTO;
import com.aportme.aportme.backend.component.address.dto.AddressDTO;
import com.aportme.aportme.backend.component.address.entity.Address;
import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.user.enums.Role;
import com.aportme.aportme.backend.component.user.repository.UserRepository;
import com.aportme.aportme.backend.component.userInfo.dto.AddUserInfoDTO;
import com.aportme.aportme.backend.component.userInfo.dto.UserInfoDTO;
import com.aportme.aportme.backend.component.userInfo.service.UserInfoService;
import com.aportme.aportme.backend.security.exception.BadUserPasswordException;
import com.aportme.aportme.backend.security.exception.UserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final UserInfoService userInfoService;

    public void registerUser(User user) throws Exception {
        User userFromDB = userRepository.findByEmail(user.getEmail());
        if(userFromDB != null) {
            throw new UserAlreadyExistsException();
        }
        if(user.getPassword().length() < 8 || user.getPassword().length() > 256){
            throw new BadUserPasswordException();
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        userInfoService.create(user.getEmail(), new AddUserInfoDTO("", "", "", new AddOrUpdateAddressDTO()));
    }

}
