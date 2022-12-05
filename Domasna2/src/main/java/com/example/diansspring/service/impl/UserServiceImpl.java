package com.example.diansspring.service.impl;

import com.example.diansspring.model.User;
import com.example.diansspring.model.enums.Role;
import com.example.diansspring.repository.UserRepository;
import com.example.diansspring.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String name, String surname, Role role) {
        return null;
    }
}
