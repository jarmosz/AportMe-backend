package com.aportme.aportme.backend.service;

import com.aportme.aportme.backend.dto.DTOEntity;
import com.aportme.aportme.backend.dto.user.UserDTO;
import com.aportme.aportme.backend.entity.user.User;
import com.aportme.aportme.backend.repository.UserRepository;
import com.aportme.aportme.backend.utils.EntityDTOConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityDTOConverter entityDTOConverter;

    public DTOEntity getUserById(Long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        if(userFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(userFromDB.get(), new UserDTO());
    }
}
