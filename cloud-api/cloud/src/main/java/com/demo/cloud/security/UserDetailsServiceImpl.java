package com.demo.cloud.security;

import com.demo.cloud.core.error.exceptions.EntityNotFoundException;
import com.demo.cloud.model.User;
import com.demo.cloud.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService service;

    public UserDetailsServiceImpl(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User found = getByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                found.getUsername(),
                found.getPassword(),
                List.of(new SimpleGrantedAuthority(found.getRole().getName()))
        );
    }

    private User getByUsername(String username) {
        try {
            return service.getByUsername(username);
        } catch (EntityNotFoundException ex) {
            throw new UsernameNotFoundException("Invalid username", ex);
        }
    }
}
