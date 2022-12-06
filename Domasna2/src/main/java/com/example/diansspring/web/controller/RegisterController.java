package com.example.diansspring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("mainCssFile", "register.css");
        model.addAttribute("mainBodyContent", "register");

        return "master-template";
    }

    @PostMapping
    public String doRegister() {
        return "home";
    }
}
