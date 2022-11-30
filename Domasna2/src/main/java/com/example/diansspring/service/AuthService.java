package com.example.diansspring.service;

import com.example.diansspring.model.User;

public interface AuthService {
    User login(String username, String password);
}
