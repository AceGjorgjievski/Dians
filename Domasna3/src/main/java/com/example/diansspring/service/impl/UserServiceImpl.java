package com.example.diansspring.service.impl;

import com.example.diansspring.model.User;
import com.example.diansspring.model.enums.Role;
import com.example.diansspring.model.exceptions.CaptchaDoesNotMatchException;
import com.example.diansspring.model.exceptions.PasswordsDoNotMatchException;
import com.example.diansspring.model.exceptions.UserAlreadyExistsException;
import com.example.diansspring.repository.UserRepository;
import com.example.diansspring.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User register(String username, String password, String repeatPassword, String email, String niceName, String hiddenCaptcha, String captcha) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty()) {
            throw new BadCredentialsException("Missing username or password");
        }
        if (repeatPassword == null || repeatPassword.isEmpty() || !password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }
        if (this.userRepository.findByUsername(username).isPresent() || this.userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        if (hiddenCaptcha == null || !hiddenCaptcha.equals(captcha)) {
            throw new CaptchaDoesNotMatchException();
        }

        User user = new User(username, passwordEncoder.encode(password), email, niceName);
        return userRepository.save(user);
    }
}
