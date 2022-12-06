package com.example.diansspring.service;

import com.example.diansspring.model.User;
import com.example.diansspring.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String email, String niceName);
}
