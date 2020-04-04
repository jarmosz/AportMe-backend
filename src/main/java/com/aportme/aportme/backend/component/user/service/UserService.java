package com.aportme.aportme.backend.component.user.service;

import com.aportme.aportme.backend.component.user.dto.UserDTO;
import com.aportme.aportme.backend.component.user.entity.User;
import com.aportme.aportme.backend.component.user.repository.UserRepository;
import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.aportme.aportme.backend.utils.dto.EntityDTOConverter;
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
