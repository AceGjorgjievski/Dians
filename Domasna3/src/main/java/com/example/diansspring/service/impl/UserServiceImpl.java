package com.example.diansspring.service.impl;

import com.example.diansspring.model.Facility;
import com.example.diansspring.model.User;
import com.example.diansspring.model.enums.Role;
import com.example.diansspring.model.exceptions.CaptchaDoesNotMatchException;
import com.example.diansspring.model.exceptions.PasswordsDoNotMatchException;
import com.example.diansspring.model.exceptions.UserAlreadyExistsException;
import com.example.diansspring.repository.FacilityRepository;
import com.example.diansspring.repository.UserRepository;
import com.example.diansspring.service.FacilityService;
import com.example.diansspring.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FacilityService facilityService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, FacilityService facilityService) {
        this.userRepository = userRepository;
        this.facilityService = facilityService;
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

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<Facility> updateFavouritesList(String username, Long facilityId) {
        User user = this.findByUsername(username);
        Facility facility = this.facilityService.findById(facilityId);

        if (Objects.equals(user, null)) return null;
        if (Objects.equals(facility, null)) return getFavouriteFacilities(user);

        String res = "";

        List<Facility> userFavourites = getFavouriteFacilities(user);
        if (userFavourites.contains(facility)) {
            userFavourites.remove(facility);
        }
        else {
            userFavourites.add(facility);
        }
        user.setFavouriteFacilities(userFavourites);
        this.userRepository.save(user);

        return getFavouriteFacilities(user);
    }

    private List<Facility> getFavouriteFacilities(User user) {
        return user.getFavouriteFacilities().stream().peek(e -> {
            e.setFavouritedByUsers(null);
        }).collect(Collectors.toList());
    }
}
