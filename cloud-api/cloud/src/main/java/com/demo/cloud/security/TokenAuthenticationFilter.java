package com.demo.cloud.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenUtil tokenUtil;
    private final UserDetailsService service;

    public TokenAuthenticationFilter(TokenUtil tokenUtil, UserDetailsService service) {
        this.tokenUtil = tokenUtil;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = tokenUtil.getToken(request);

        if (authToken != null) {
            String username = tokenUtil.getUsernameFromToken(authToken);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails details = service.loadUserByUsername(username);

                if (tokenUtil.validateToken(authToken, details)) {
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(authToken, details);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
