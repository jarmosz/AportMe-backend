package com.aportme.backend.service;

import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.user.AuthUserDTO;
import com.aportme.backend.entity.dto.user.ChangeUserPasswordDTO;
import com.aportme.backend.entity.dto.user.ResetUserPasswordDTO;
import com.aportme.backend.event.SendResetPasswordLinkEvent;
import com.aportme.backend.exception.UserAlreadyExistsException;
import com.aportme.backend.exception.UserNotFoundException;
import com.aportme.backend.exception.WrongChangePasswordDataException;
import com.aportme.backend.exception.WrongUserCredentialsException;
import com.aportme.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher eventPublisher;
    private final ModelMapper modelMapper;

    private static final String EMAIL_REGEX_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
    private static final String PASSWORD_REGEX_PATTERN = "^.{8,256}$";

    private boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX_PATTERN);
    }

    private boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX_PATTERN);
    }

    private boolean validateData(AuthUserDTO userDTO) {
        return validateEmail(userDTO.getEmail()) && validatePassword(userDTO.getPassword());
    }

    private boolean isNewPasswordDataValid(ChangeUserPasswordDTO passwords, User loggedUser) {
        return validatePassword(passwords.getOldPassword())
                && passwordEncoder.matches(passwords.getOldPassword(), loggedUser.getPassword())
                && validatePassword(passwords.getNewPassword())
                && (passwords.getNewPassword().equals(passwords.getRepeatedNewPassword()));
    }

    public void registerUser(AuthUserDTO userDTO) {
        if (validateData(userDTO)) {
            Boolean isUserRegistered = userRepository.existsByEmail(userDTO.getEmail());
            if (!isUserRegistered) {
                User user = modelMapper.map(userDTO, User.class);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                saveUser(user);
            } else {
                throw new UserAlreadyExistsException();
            }
        } else {
            throw new WrongUserCredentialsException();
        }
    }

    public void sendResetPasswordLink(ResetUserPasswordDTO resetUserPasswordDTO) {
        String userEmail = resetUserPasswordDTO.getEmail();
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        eventPublisher.publishEvent(new SendResetPasswordLinkEvent(user, DateTime.now()));
    }

    public void changeUserPassword(ChangeUserPasswordDTO passwords) {
        User loggedUser = getLoggedUser();
        String encodedNewPassword = passwordEncoder.encode(passwords.getNewPassword());
        if (isNewPasswordDataValid(passwords, loggedUser)) {
            loggedUser.setPassword(encodedNewPassword);
            userRepository.save(loggedUser);
        } else {
            throw new WrongChangePasswordDataException();
        }
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByEmail(String email){ return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);}

    public User getLoggedUser() {
        String userEmail = authenticationService.getLoggedUsername();
        return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
