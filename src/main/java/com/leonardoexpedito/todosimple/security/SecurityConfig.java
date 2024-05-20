package com.leonardoexpedito.todosimple.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .mvcMatchers(HttpMethod.POST, "/auth").permitAll()
                                .mvcMatchers(HttpMethod.GET, "/user").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.POST, "/user").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.PUT, "/user").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.DELETE, "/user").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.POST, "/task").hasRole("USER")
                                .mvcMatchers(HttpMethod.GET, "/task").hasRole("USER")
                                .mvcMatchers(HttpMethod.PUT, "/task").hasRole("USER")
                                .mvcMatchers(HttpMethod.DELETE, "/task").hasRole("USER")
                                .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
