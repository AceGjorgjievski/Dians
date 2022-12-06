package com.example.diansspring.web.controller;

import com.example.diansspring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("pageTitle", "Register - Findify");
        model.addAttribute("mainCssFile", "register.css");
        model.addAttribute("mainBodyContent", "register");

        return "master-template";
    }

    @PostMapping
    public String doRegister(@RequestParam String username, @RequestParam String password,  @RequestParam String repeatPassword, @RequestParam String email, @RequestParam String niceName) {
        try {
            this.userService.register(username, password, repeatPassword, email, niceName);
            return "redirect:/login?successfullyRegistered";
        } catch (Exception exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
