package com.example.diansspring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping
    public String doRegister() {
        return "home";
    }
}
