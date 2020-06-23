package com.aportme.backend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated();
                // this disables session creation on Spring Security
        http.headers().frameOptions().disable();
    }
}
