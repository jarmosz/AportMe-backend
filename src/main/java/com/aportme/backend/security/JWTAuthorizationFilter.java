package com.aportme.backend.security;

import com.aportme.backend.entity.enums.Role;
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
            try {
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
            } catch (JWTVerificationException e) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        chain.doFilter(httpRequest, httpResponse);
    }

    private Authentication getAuthentication(String token) throws JWTVerificationException {

        final Long id;
        final Role role;
        final String email;
        String[] tokenParts = token.split(" ");
        String mainToken = tokenParts[1];
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET.getBytes()))
                .build()
                .verify(mainToken);

        id = jwt.getClaim("id").asLong();
        email = jwt.getSubject().toLowerCase();
        role = jwt.getClaim("role").as(Role.class);

        return new JWTAuthentication(id, email, role);
    }

}
