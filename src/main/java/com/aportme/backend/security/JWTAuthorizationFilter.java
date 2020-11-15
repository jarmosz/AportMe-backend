package com.aportme.backend.security;

import com.aportme.backend.entity.enums.Role;
import com.aportme.backend.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthorizationFilter extends GenericFilterBean {

    private final UserRepository userRepository;

    public JWTAuthorizationFilter(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Value("${jwt.header.string}")
    private String HEADER_STRING;

    @Value("${jwt.encryption.secret}")
    private String SECRET;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = httpRequest.getHeader(HEADER_STRING);
        if (token != null) {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private Authentication getAuthentication(String token)   {

        final String username;
        final String email;
        final Role role;
        try {
            String[] tokenParts = token.split(" ");
            String mainToken = tokenParts[1];
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
                    .build()
                    .verify(mainToken);
            username = jwt.getSubject();
            role = Role.valueOf(jwt.getClaim("role").asString());
            email = jwt.getClaim("email").toString();
        } catch (JWTVerificationException e) {
            return null;
        }

        final Long userId;
        try {
            userId = Long.valueOf(username);
        } catch (NumberFormatException e) {
            return null;
        }

        return new JWTAuthentication(userId, email, role);
    }

}
