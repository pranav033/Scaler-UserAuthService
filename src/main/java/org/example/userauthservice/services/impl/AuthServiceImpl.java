package org.example.userauthservice.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.example.userauthservice.exceptions.PasswordMismatchException;
import org.example.userauthservice.exceptions.UserAlreadyExistsException;
import org.example.userauthservice.exceptions.UserNotSignedUpException;
import org.example.userauthservice.models.Session;
import org.example.userauthservice.models.Status;
import org.example.userauthservice.models.User;
import org.example.userauthservice.repositories.SessionRepo;
import org.example.userauthservice.repositories.UserRepo;
import org.example.userauthservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User signup(String name, String email, String password, String phonenumber) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isPresent())
        {
            throw new UserAlreadyExistsException("User already exists");
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setPhoneNumber(phonenumber);
        return userRepo.save(user);
    }

    @Override
    public Pair<User,String> login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty())
        {
            throw new UserNotSignedUpException("Please sign up first.");
        }
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
        {
            throw new PasswordMismatchException("Incorrect password");
        }

        //generate jwt

//                        String message = "{\n" +
//                "   \"email\": \"anurag@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"2ndApril2026\"\n" +
//                "}";
//
//        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",user.getId());
        claims.put("iss","scaler");
        long currentTimeMillis = System.currentTimeMillis();
        claims.put("gen",currentTimeMillis);
        claims.put("exp",currentTimeMillis+1234567890);
        claims.put("scope",user.getRoles());
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey secretKey = algorithm.key().build();
        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setStatus(Status.ACTIVE);
        sessionRepo.save(session);

        return new Pair<User,String>(user,token);
    }

    @Override
    public Boolean validateToken(String token, Long userID) {
        return null;
    }
}
