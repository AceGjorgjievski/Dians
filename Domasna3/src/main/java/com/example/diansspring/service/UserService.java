package com.example.diansspring.service;

import com.example.diansspring.model.Facility;
import com.example.diansspring.model.User;
import com.example.diansspring.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    User register(String username, String password, String repeatPassword, String email, String niceName, String hiddenCaptcha, String captcha);
    User findByUsername(String username);
    List<Facility> updateFavouritesList(String username, Long facilityId);
}
