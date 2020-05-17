package com.aportme.aportme.backend.component.auth.dto;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizedDTO implements DTOEntity {

    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private Long expiresIn;

    @JsonProperty(value = "refresh_expires_in")
    private Long refreshExpiresIn;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    @JsonProperty(value = "token_type")
    private String tokenType;

    @JsonProperty(value = "not-before-policy")
    private int notBeforePolicy;

    @JsonProperty(value = "session_state")
    private String sessionState;

    @JsonProperty(value = "scope")
    private String scope;
}
