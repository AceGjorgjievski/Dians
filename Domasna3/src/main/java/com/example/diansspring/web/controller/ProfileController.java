package com.example.diansspring.web.controller;

import com.example.diansspring.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("pageTitle", "My Account - Findify");
        model.addAttribute("mainBodyContent", "profile");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(auth.getPrincipal(), "anonymousUser")) {
            User currentUser = (User) auth.getPrincipal();
            model.addAttribute("currentUserUsername", currentUser.getUsername());
            model.addAttribute("currentUserNiceName", currentUser.getNiceName());
        }

        return "master-template";
    }
}
