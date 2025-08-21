package org.example.userauthservice.services.impl;

import org.example.userauthservice.models.User;
import org.example.userauthservice.repositories.UserRepo;
import org.example.userauthservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isEmpty()) return null;
        System.out.println(userOptional.get().getEmail());
        return userOptional.get();


    }
}
