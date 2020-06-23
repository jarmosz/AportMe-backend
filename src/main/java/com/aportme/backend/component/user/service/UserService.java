package com.aportme.backend.component.user.service;

import com.aportme.backend.component.activationToken.event.OnRegistrationCompleteEvent;
import com.aportme.backend.component.user.dto.UserDTO;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.enums.Role;
import com.aportme.backend.component.user.exception.UserAlreadyExistsException;
import com.aportme.backend.component.user.exception.WrongUserCredentialsException;
import com.aportme.backend.component.user.repository.UserRepository;
import com.aportme.backend.utils.dto.DTOEntity;
import com.aportme.backend.utils.dto.EntityDTOConverter;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final EntityDTOConverter entityDTOConverter;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ApplicationEventPublisher eventPublisher;

    public DTOEntity getUserById(Long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        if(userFromDB.isEmpty()) {
            return null;
        }
        return entityDTOConverter.convertToDto(userFromDB.get(), new UserDTO());
    }

    private boolean validateEmail(String email){
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return email.matches(regex);
    }

    private boolean validatePassword(String password){
        return password.length() > 8;
    }

    public void registerUser(User user, HttpServletRequest request){
        if(validateEmail(user.getEmail()) && validatePassword(user.getPassword())){
            Optional<User> userFromDB = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
            if(!userFromDB.isEmpty()) {
                throw new UserAlreadyExistsException();
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(Role.USER);
            user.setActive(false);
            userRepository.save(user);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, DateTime.now(), request.getContextPath()));
        }
        else {
            throw new WrongUserCredentialsException();
        }
    }
}
