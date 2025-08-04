package org.example.userauthservice.services;

import org.example.userauthservice.models.User;

public interface AuthService {

    User signup(String name, String email, String password, String phonenumber);

    User login(String email, String password);

    Boolean validateToken(String token, Long userID);
}
