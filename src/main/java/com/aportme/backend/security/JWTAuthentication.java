package com.aportme.backend.security;

import com.aportme.backend.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthentication implements Authentication {

    private final long userId;
    private final String email;
    private final Collection<GrantedAuthority> grantedAuthorities;

    public JWTAuthentication(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.grantedAuthorities = user.getAuthorities();
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

    public long getUserId() {
        return userId;
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
