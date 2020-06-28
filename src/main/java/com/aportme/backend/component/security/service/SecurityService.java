package com.aportme.backend.component.security.service;

import com.aportme.backend.component.security.config.JWTAuthentication;
import com.aportme.backend.component.security.config.JWTAuthorizationFilter;
import com.aportme.backend.component.security.dto.TokenPairDTO;
import com.aportme.backend.component.security.dto.UserLoginDTO;
import com.aportme.backend.component.security.exception.UserDoesNotExistsException;
import com.aportme.backend.component.security.exception.WrongPasswordException;
import com.aportme.backend.component.user.entity.User;
import com.aportme.backend.component.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JWTAuthorizationFilter jwtAuthorizationFilter;


    public TokenPairDTO loginUser(UserLoginDTO userLoginDTO){
        return userRepository.findByEmail(userLoginDTO.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
                        return createTokenPair(user);
                    } else {
                        throw new WrongPasswordException();
                    }
                })
                .orElseThrow(UserDoesNotExistsException::new);
    }

    private TokenPairDTO createTokenPair(User user){
        String accessToken = jwtAuthorizationFilter.generateAccessToken(user.getId());
        String refreshToken = "";

        return new TokenPairDTO(accessToken, refreshToken);
    }

    public long getLoggedUserId() {
        JWTAuthentication authentication = (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }

}
