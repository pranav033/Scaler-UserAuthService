package org.example.userauthservice.services.impl;

import org.example.userauthservice.models.User;
import org.example.userauthservice.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public User signup(String name, String email, String password, String phonenumber) {
        return null;
    }

    @Override
    public User login(String email, String password) {
        return null;
    }

    @Override
    public Boolean validateToken(String token, Long userID) {
        return null;
    }
}
