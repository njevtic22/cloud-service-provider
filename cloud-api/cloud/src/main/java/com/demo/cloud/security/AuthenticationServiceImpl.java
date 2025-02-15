package com.demo.cloud.security;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
import com.demo.cloud.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
//    Service is not used in order to break circular dependency
//    private final UserService service;

    private final UserRepository repository;
    private final TokenUtil tokenUtil;
    private final AuthenticationManager authManager;

    public AuthenticationServiceImpl(UserRepository repository, TokenUtil tokenUtil, AuthenticationManager authManager) {
        this.repository = repository;
        this.tokenUtil = tokenUtil;
        this.authManager = authManager;
    }

    @Override
    public Pair<String, String> authenticate(String username, String password) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);

        org.springframework.security.core.userdetails.User principal = getPrincipal(auth);

        String role = new ArrayList<>(principal.getAuthorities()).get(0).getAuthority();
        String token = tokenUtil.generateToken(username);

        return new Pair<>(token, role);
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
        return repository.findByUsernameAndArchivedFalse(principal.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User", "username", principal.getUsername()));
    }
}
