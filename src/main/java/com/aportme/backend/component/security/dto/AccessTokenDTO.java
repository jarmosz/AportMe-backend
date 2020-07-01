package com.aportme.backend.component.security.dto;

import com.aportme.backend.utils.dto.DTOEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccessTokenDTO implements DTOEntity {
    private String accessToken;
}
