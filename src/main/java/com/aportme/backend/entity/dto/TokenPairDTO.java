package com.aportme.backend.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenPairDTO {

    private String authToken;

    private String refreshToken;
}
