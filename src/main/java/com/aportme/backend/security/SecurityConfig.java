package com.aportme.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors()
                .and()
                .csrf().disable()
                .addFilterAfter(jwtAuthorizationFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/logout").permitAll()
                .antMatchers(HttpMethod.POST, "/api/refreshToken").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/pets").permitAll()
                .antMatchers(HttpMethod.GET,"/api/pets/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/api/users/password/reset/link").permitAll()
                .antMatchers(HttpMethod.GET,"/api/users/password/token/validate").permitAll()
                .antMatchers(HttpMethod.POST,"/api/users/password/token/reset").permitAll()
                .antMatchers(HttpMethod.GET,"/api/foundations").permitAll()
                .antMatchers(HttpMethod.GET,"/api/foundations/{id}").permitAll()
                .anyRequest()
                .authenticated();


        http
                .headers()
                .frameOptions()
                .disable();

        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setHeader("WWW-Authenticate", "Bearer");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        });
    }


    @Bean
    @Profile("!prod")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowCredentials(true)
                        .allowedOrigins("http://localhost:8081")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                        .allowedHeaders("Authorization", "Content-Type", "Access-Control-Allow-Headers", "X-Requested-With", "Accept", "X-XSRF-TOKEN")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    @Profile("prod")
    public WebMvcConfigurer productionCorsConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowCredentials(true)
                        .allowedOrigins("https://146.59.17.80:8080", "https://www.aportme.com:8080", "https://aportme.com:8080")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                        .allowedHeaders("Authorization", "Content-Type", "Access-Control-Allow-Headers", "X-Requested-With", "Accept", "X-XSRF-TOKEN")
                        .exposedHeaders("Authorization");
            }
        };
    }
}
