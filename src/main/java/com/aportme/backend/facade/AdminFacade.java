package com.aportme.backend.facade;

import com.aportme.backend.entity.Address;
import com.aportme.backend.entity.Foundation;
import com.aportme.backend.entity.User;
import com.aportme.backend.entity.dto.foundation.AddFoundationDTO;
import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.entity.survey.FoundationSurvey;
import com.aportme.backend.service.AddressService;
import com.aportme.backend.service.FoundationService;
import com.aportme.backend.service.UserService;
import com.aportme.backend.service.survey.FoundationSurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminFacade {

    private final FoundationService foundationService;
    private final AddressService addressService;
    private final FoundationSurveyService foundationSurveyService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void createFoundation(AddFoundationDTO dto) {
        User foundationUser = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.FOUNDATION)
                .build();

        userService.saveUser(foundationUser);

        Address address = addressService.mapToEntity(dto.getAddress());
        addressService.save(address);

        Foundation foundation = foundationService.mapTo(dto, Foundation.class);
        foundation.setUser(foundationUser);
        foundation.setAddress(address);

        foundationService.save(foundation);

        FoundationSurvey foundationSurvey = foundationSurveyService.findOrCreateFoundationSurvey(foundation);
        foundation.setFoundationSurvey(foundationSurvey);

        foundationService.save(foundation);
    }
}