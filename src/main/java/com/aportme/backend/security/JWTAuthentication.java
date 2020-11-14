package com.aportme.backend.security;

import com.aportme.backend.entity.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class JWTAuthentication implements Authentication {

    private final long userId;
    private final String email;
    private final Collection<GrantedAuthority> grantedAuthorities;

    public JWTAuthentication(Long userId, String email, Role role) {
        this.userId = userId;
        this.email = email;
        this.grantedAuthorities = Arrays.asList(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Long getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException("JWT authentication is always authenticated");
    }

    @Override
    public String getName() {
        return this.email;
    }
}
