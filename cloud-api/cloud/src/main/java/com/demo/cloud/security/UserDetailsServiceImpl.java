package com.demo.cloud.security;

import com.demo.cloud.model.User;
import com.demo.cloud.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
//    Service is not used in order to break dependency cycle
//    private final UserService service;

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User found = repository.findByUsernameAndArchivedFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username"));

        return new org.springframework.security.core.userdetails.User(
                found.getUsername(),
                found.getPassword(),
                List.of(new SimpleGrantedAuthority(found.getRole().getName()))
        );
    }
}
