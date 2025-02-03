package com.demo.cloud.security;

import com.demo.cloud.model.User;
import com.demo.cloud.service.UserService;
import com.demo.cloud.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService service;
    private final AuthenticationManager authManager;

    public AuthenticationServiceImpl(UserService service, AuthenticationManager authManager) {
        this.service = service;
        this.authManager = authManager;
    }

    @Override
    public Pair<String, String> authenticate(String username, String password) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        org.springframework.security.core.userdetails.User principal = getPrincipal(auth);

        String role = new ArrayList<>(principal.getAuthorities()).get(0).getAuthority();

        // TODO: implement jwt
        return new Pair<>("token", role);
    }

    @Override
    public User getAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUser(auth);
    }

    private org.springframework.security.core.userdetails.User getPrincipal(Authentication auth) {
        return (org.springframework.security.core.userdetails.User) auth.getPrincipal();
    }

    private User getUser(Authentication auth) {
        org.springframework.security.core.userdetails.User principal = getPrincipal(auth);
        return service.getByUsername(principal.getUsername());
    }
}
