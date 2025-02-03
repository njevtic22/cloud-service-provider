package com.demo.cloud.security;

import com.demo.cloud.model.User;
import com.demo.cloud.util.Pair;

public interface AuthenticationService {
    Pair<String, String> authenticate(String username, String password);

    User getAuthenticated();
}
