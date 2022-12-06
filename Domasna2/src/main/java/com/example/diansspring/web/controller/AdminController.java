package com.example.diansspring.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String getAdminDashboard(Model model) {
        model.addAttribute("pageTitle", "Admin Dashboard - Findify");
        model.addAttribute("mainCssFile", "home.css");
        model.addAttribute("mainBodyContent", "home");

        return "master-template";
    }
}
