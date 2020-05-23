package com.aportme.aportme.backend.component.auth.dto;

import com.aportme.aportme.backend.utils.dto.DTOEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GetTokenDTO implements DTOEntity {

    private String username;

    private String password;

    @JsonProperty(value = "client_id")
    private String clientId;

    @JsonProperty(value = "grant_type")
    private String grantType;

    @JsonProperty(value = "refresh_token")
    private String refreshToken;
}
