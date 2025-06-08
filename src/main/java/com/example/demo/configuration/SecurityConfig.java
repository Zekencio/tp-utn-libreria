package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Todas las rutas permitidas en un principio para poder probar sin que Postman nos rompa las bolas con la autenticación.
    // A medida que avancemos con el proyecto protegemos las rutas que nos sean convenientes
    // - Eze
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(auth ->
                auth.requestMatchers("api/**").permitAll()).build();
    }

}
