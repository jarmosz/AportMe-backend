package com.aportme.backend.service;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.exception.UserNotFoundException;
import com.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final ModelMapper modelMapper;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).orElseThrow(UserNotFoundException::new);
    }

    public User getLoggedUser() {
        String email = authenticationService.getLoggedUsername();
        return findByEmail(email);
    }

    public Boolean isUserExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User mapToUser(AuthUserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}
