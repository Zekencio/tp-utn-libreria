package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //dudosa funcionalidad
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth ->
        auth.requestMatchers("/api/cards").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/genres","/api/authors").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/books","api/books/**").permitAll()

                .requestMatchers(HttpMethod.POST,"/api/genres","/api/books","/api/authors").authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        return http.build();
    }

    @Bean
    public UserDetailsService testUser(){
        UserDetails userDetails= User.withUsername("admin").password(passwordEncoder().encode("123")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
//
//csrf(csrf -> csrf.disable()) // Desactivamos CSRF (por ser API REST)
//        .authorizeHttpRequests(auth -> auth
//        .requestMatchers("/api/books").permitAll()
//                        .requestMatchers("/api/authors").permitAll()
//                        .requestMatchers("/api/genres/**").permitAll()
//                        .requestMatchers("/api/sale/**").authenticated()
//                        .anyRequest().authenticated()
//                )
//                        .build();