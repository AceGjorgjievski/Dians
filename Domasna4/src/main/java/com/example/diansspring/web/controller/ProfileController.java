package com.example.diansspring.web.controller;

import com.example.diansspring.model.User;
import com.example.diansspring.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("pageTitle", "My Account - Findify");
        model.addAttribute("mainBodyContent", "profile");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!Objects.equals(auth.getPrincipal(), "anonymousUser")) {
            User currentUser = (User) auth.getPrincipal();
            model.addAttribute("currentUserUsername", currentUser.getUsername());

            User user = this.userService.findByUsername(currentUser.getUsername());
            model.addAttribute("currentUserNiceName", user.getNiceName());
        }

        return "master-template";
    }

    @PostMapping("/save")
    public String updateProfile(@RequestParam String username, @RequestParam(required = false) String niceName) {
        if (!Objects.equals(niceName, null) && !Objects.equals(niceName, "")) {
            this.userService.update(username, niceName);
        }

        return "redirect:/profile";
    }
}
