package com.aportme.aportme.backend.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/pets").antMatchers("/api/auth/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().antMatcher("api/auth/login").antMatcher("api/pets");

        http.cors().and()
                .authorizeRequests()
                .antMatchers("/api/foundations").hasAuthority("SCOPE_foundation")
                .anyRequest().denyAll()
                .and()
                .oauth2ResourceServer()
                .jwt();
    }
}
