package com.aportme.backend.component.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,"/activateAccount").permitAll()
                .antMatchers(HttpMethod.GET,"/api/foundations").permitAll()
                .antMatchers(HttpMethod.GET,"/api/foundations/*").permitAll()
                .antMatchers(HttpMethod.GET,"/api/pets").permitAll()
                .antMatchers(HttpMethod.GET,"/api/pets/*").permitAll()
                .anyRequest().authenticated();
                // this disables session creation on Spring Security
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.setHeader("WWW-Authenticate", "Bearer");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        });
    }

}
