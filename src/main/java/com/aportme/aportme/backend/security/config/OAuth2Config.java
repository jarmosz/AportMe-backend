package com.aportme.aportme.backend.security.config;

import com.google.common.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth.client.id}")
    private String clientid;

    @Value("${oauth.client.secret}")
    private String clientSecret;

    @Value("${oauth.accessTokenExpirationTime}")
    private int accessTokenExpirationTime;

    @Value("${oauth.refreshTokenExpirationTime}")
    private int refreshTokenExpirationTime;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() throws IOException {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(readRSAKey("private-key.pem"));
        converter.setVerifierKey(readRSAKey("public-key.pem"));
        return converter;
    }

    private String readRSAKey(String fileName) throws IOException {
        return Resources.toString(Resources.getResource(fileName), StandardCharsets.UTF_8);
    }

    @Bean
    public JwtTokenStore tokenStore() throws IOException {
        return new JwtTokenStore(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientid).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(accessTokenExpirationTime)
                .refreshTokenValiditySeconds(refreshTokenExpirationTime);

    }
}
