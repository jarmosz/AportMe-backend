package com.aportme.backend.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    FOUNDATION,
    ADMIN,
    ;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
