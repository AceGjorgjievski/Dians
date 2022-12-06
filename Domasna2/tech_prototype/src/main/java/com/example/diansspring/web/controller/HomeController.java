package com.example.diansspring.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/home"})
public class HomeController {

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("pageTitle", "Home - Findify");
        model.addAttribute("mainCssFile", "home.css");
        model.addAttribute("mainBodyContent", "home");

        return "master-template";
    }
}
