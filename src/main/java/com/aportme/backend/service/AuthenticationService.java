package com.aportme.backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getLoggedUserName() {
        return getAuthentication().getName();
    }

    public Long getLoggedUserId() {
        return (Long) getAuthentication().getPrincipal();
    }
}
