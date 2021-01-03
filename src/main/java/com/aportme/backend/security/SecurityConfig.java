package com.aportme.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private String[] permitUrls = {
            "/favicon.ico",
            "/",
            "/{_:^(?!index\\\\.html|api).*$}",
            "/index.html",
            "/js/**",
            "/img/**",
            "/static/**",
            "/css/**",
            "/fonts/**",
            "/assets/**",
            "/_ah/**"};

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(permitUrls);
    }

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
                .antMatchers(HttpMethod.POST, "/api/authorization/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/authorization/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/authorization/refresh-token").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/pets").permitAll()
                .antMatchers(HttpMethod.GET,"/api/pets/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/api/reset-password/generate-link").permitAll()
                .antMatchers(HttpMethod.GET,"/api/reset-password/validate-token").permitAll()
                .antMatchers(HttpMethod.POST,"/api/reset-password").permitAll()
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
                        .allowedOrigins("https://146.59.17.80x", "https://www.aportme.com", "https://aportme.com")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                        .allowedHeaders("Authorization", "Content-Type", "Access-Control-Allow-Headers", "X-Requested-With", "Accept", "X-XSRF-TOKEN")
                        .exposedHeaders("Authorization");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/index.html")
                        .setCacheControl(CacheControl.noStore())
                        .addResourceLocations("classpath:/static/");
            }
        };
    }
}
