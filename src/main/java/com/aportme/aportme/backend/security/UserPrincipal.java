package com.aportme.aportme.backend.security;

import com.aportme.aportme.backend.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class UserPrincipal implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.user.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.user.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}
