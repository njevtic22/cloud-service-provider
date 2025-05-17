package com.demo.cloud.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenUtil tokenUtil;
    private final UserDetailsServiceImpl service;

    public TokenAuthenticationFilter(TokenUtil tokenUtil, UserDetailsServiceImpl service) {
        this.tokenUtil = tokenUtil;
        this.service = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = tokenUtil.getToken(request);

        if (authToken != null) {
            Long userId = tokenUtil.getIdFromToken(authToken);

            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                com.demo.cloud.model.User found = service.loadUserById(userId);

                if (tokenUtil.validateToken(authToken, found.getId())) {
                    UserDetails userDetails = new User(found.getUsername(), found.getPassword(), List.of(new SimpleGrantedAuthority(found.getRole().getName())));
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(authToken, userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
