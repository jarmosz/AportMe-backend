package com.aportme.backend.facade;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.exception.UserAlreadyExistsException;
import com.aportme.backend.service.AddressService;
import com.aportme.backend.service.CanonicalService;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.service.UserService;
import com.aportme.backend.service.survey.FoundationSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class AdminFacade {

    private final FoundationService foundationService;
    private final AddressService addressService;
    private final FoundationSurveyService foundationSurveyService;
    private final UserService userService;
    private final CanonicalService canonicalService;
    private final PasswordEncoder passwordEncoder;

    public void createFoundation(AddFoundationDTO dto) {
        String email = dto.getEmail().toLowerCase();
        Boolean isUserRegistered = userService.isUserExists(email);

        if (isUserRegistered) {
            throw new UserAlreadyExistsException();
        }

        User foundationUser = User.builder()
                .email(email)
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.FOUNDATION)
                .build();

        userService.saveUser(foundationUser);

        Address address = addressService.mapToEntity(dto.getAddress());
        addressService.save(address);

        String searchableName = canonicalService.replaceCanonicalLetters(dto.getName().toLowerCase());

        Foundation foundation = foundationService.mapTo(dto, Foundation.class);
        foundation.setUser(foundationUser);
        foundation.setSearchableName(searchableName);
        foundation.setAddress(address);

        foundationService.save(foundation);

        FoundationSurvey foundationSurvey = foundationSurveyService.findOrCreateFoundationSurvey(foundation);
        foundation.setFoundationSurvey(foundationSurvey);

        foundationService.save(foundation);
    }
}
