package com.example.diansspring.service.impl;

import com.example.diansspring.model.User;
import com.example.diansspring.model.exceptions.InvalidArgumentException;
import com.example.diansspring.model.exceptions.InvalidUserCredentialsException;
import com.example.diansspring.repository.UserRepository;
import com.example.diansspring.service.AuthService;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public User login(String username, String password) {
//        if (username == null || username.isEmpty() ||
//                password == null || password.isEmpty()) throw new InvalidArgumentException(username, password);
//
//        return this.userRepository.findByUsernameAndPassword(username, password)
//                .orElseThrow(InvalidUserCredentialsException::new);
//    }
}

