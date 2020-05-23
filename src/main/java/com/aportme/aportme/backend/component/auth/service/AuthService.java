package com.aportme.aportme.backend.component.auth.service;

import com.aportme.aportme.backend.component.auth.TokenType;
import com.aportme.aportme.backend.component.auth.dto.AuthorizedDTO;
import com.aportme.aportme.backend.component.auth.dto.GetTokenDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private static final String authUrl = "http://localhost:8180/auth/realms/AportMe/protocol/openid-connect/token";

    public ResponseEntity<AuthorizedDTO> getToken(GetTokenDTO dto, TokenType tokenType) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> form = getRequestBodyByTokenType(dto, tokenType);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(form, headers);
        ResponseEntity<AuthorizedDTO> response = restTemplate.exchange(authUrl, HttpMethod.POST, entity, AuthorizedDTO.class);

        return createResponseEntity(response.getBody());
    }

    private MultiValueMap<String, Object> refreshTokenPayload(GetTokenDTO refreshTokenDTO) {
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("client_id", refreshTokenDTO.getClientId());
        form.add("grant_type", refreshTokenDTO.getGrantType());
        form.add("refresh_token", refreshTokenDTO.getRefreshToken());

        return form;
    }

    private MultiValueMap<String, Object> loginPayload(GetTokenDTO getTokenDTO) {
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("username", getTokenDTO.getUsername());
        form.add("password", getTokenDTO.getPassword());
        form.add("client_id", getTokenDTO.getClientId());
        form.add("grant_type", getTokenDTO.getGrantType());

        return form;
    }

    private MultiValueMap<String, Object> getRequestBodyByTokenType(GetTokenDTO entity, TokenType tokenType) {
        if (tokenType == TokenType.ACCESS) {
            return loginPayload(entity);
        } else {
            return refreshTokenPayload(entity);
        }
    }

    private <T> ResponseEntity<T> createResponseEntity(T body) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(body);
    }

}
