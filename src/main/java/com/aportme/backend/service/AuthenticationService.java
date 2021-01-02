package com.aportme.backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AuthenticationService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthentication().getAuthorities();
    }

    public String getLoggedUsername() {
        return getAuthentication().getName();
    }

    public Long getLoggedUserId() {
        if(getAuthentication() == null) {
            return null;
        }
        return getLoggedUsername().equals("anonymousUser") ? null : (Long) getAuthentication().getPrincipal();
    }
}
