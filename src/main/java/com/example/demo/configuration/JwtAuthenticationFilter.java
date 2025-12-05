package com.example.demo.configuration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Claims;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                boolean valid = jwtUtil.validateToken(token);
                log.debug("[JwtFilter] Authorization header present, token valid={} for request {}", valid, request.getRequestURI());
                if (valid) {
                    String username = jwtUtil.getUsername(token);
                    log.debug("[JwtFilter] token subject='{}'", username);
                    try {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        log.debug("[JwtFilter] Authentication set from UserDetails for '{}'", username);
                    } catch (Exception ex) {
                        try {
                            Claims claims = jwtUtil.parseClaims(token);
                            Object rolesObj = claims.get("roles");
                            java.util.List<SimpleGrantedAuthority> authorities = new java.util.ArrayList<>();
                            if (rolesObj instanceof java.util.Collection) {
                                for (Object r : (java.util.Collection<?>) rolesObj) {
                                    if (r != null) authorities.add(new SimpleGrantedAuthority(r.toString()));
                                }
                            }
                            UserDetails fallback = new org.springframework.security.core.userdetails.User(username, "", authorities);
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(fallback, null, authorities);
                            SecurityContextHolder.getContext().setAuthentication(auth);
                            log.debug("[JwtFilter] Authentication set from token claims for '{}' roles={}", username, authorities);
                        } catch (Exception inner) {
                            log.warn("[JwtFilter] Fallback authentication from token claims failed", inner);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        filterChain.doFilter(request, response);
    }
}
