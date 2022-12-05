package com.example.diansspring.service.impl;

import com.example.diansspring.model.User;
import com.example.diansspring.model.exceptions.InvalidArgumentException;
import com.example.diansspring.model.exceptions.InvalidUserCredentialsException;
import com.example.diansspring.service.AuthService;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {

    private final InMemoryUserRepository inMemoryUserRepository;

    public AuthServiceImpl(InMemoryUserRepository inMemoryUserRepository) {
        this.inMemoryUserRepository = inMemoryUserRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || username.isEmpty() ||
                password == null || password.isEmpty()) throw new InvalidArgumentException(username, password);

        return this.inMemoryUserRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}

