package com.aportme.aportme.backend.component.auth.service;

import com.aportme.aportme.backend.component.auth.dto.AuthorizedDTO;
import com.aportme.aportme.backend.component.auth.dto.LoginDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private static final String authUrl = "http://localhost:8180/auth/realms/AportMe/protocol/openid-connect/token";

    public AuthorizedDTO login(LoginDTO loginDTO) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> form = keyclockLoginPayload(loginDTO);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(form);
        ResponseEntity<AuthorizedDTO> response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, AuthorizedDTO.class);

        return response.getBody();
    }

    private MultiValueMap<String, Object> keyclockLoginPayload(LoginDTO loginDTO) {
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("username", loginDTO.getUsername());
        form.add("password", loginDTO.getPassword());
        form.add("client_id", loginDTO.getClientId());
        form.add("grant_type", loginDTO.getGrantType());

        return form;
    }
}
