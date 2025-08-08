package org.example.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.models.User;

public interface AuthService {

    User signup(String name, String email, String password, String phonenumber);

    Pair<User,String> login(String email, String password);

    Boolean validateToken(String token, Long userID);
}
