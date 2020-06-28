package com.aportme.backend.component.security.dto;

import com.aportme.backend.utils.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPairDTO implements DTOEntity {

    private String authToken;

    private String refreshToken;
}
