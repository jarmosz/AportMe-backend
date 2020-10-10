package com.aportme.backend.component.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class SecurityProperties {

    @Value("${jwt.header.string}")
    private String headerString;

    @Value("${jwt.token.type}")
    private String tokenType;

    @Value("${jwt.encryption.secret}")
    private String secret;

    @Value("${jwt.access.token.expiration.seconds}")
    private long expirationTimeInSeconds;
}
