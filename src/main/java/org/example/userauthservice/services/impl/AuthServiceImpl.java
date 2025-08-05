package org.example.userauthservice.services.impl;

import org.example.userauthservice.exceptions.PasswordMismatchException;
import org.example.userauthservice.exceptions.UserAlreadyExistsException;
import org.example.userauthservice.exceptions.UserNotSignedUpException;
import org.example.userauthservice.models.User;
import org.example.userauthservice.repositories.UserRepo;
import org.example.userauthservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepo userRepo;

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
        user.setPassword(password);
        user.setPhoneNumber(phonenumber);
        return userRepo.save(user);
    }

    @Override
    public User login(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty())
        {
            throw new UserNotSignedUpException("Please sign up first.");
        }
        User user = userOptional.get();
        if(!user.getPassword().equals(password))
        {
            throw new PasswordMismatchException("Incorrect password");
        }
        return user;
    }

    @Override
    public Boolean validateToken(String token, Long userID) {
        return null;
    }
}
