package com.example.diansspring.web.controller;


import cn.apiclub.captcha.Captcha;
import com.example.diansspring.model.User;
import com.example.diansspring.model.exceptions.InvalidUserCredentialsException;
import com.example.diansspring.service.AuthService;
import com.example.diansspring.util.CaptchaUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("pageTitle", "Login - Findify");
        model.addAttribute("mainBodyContent", "login");

        User user = new User();
        getCaptcha(user);
        model.addAttribute("captchaDetailsUser", user);

        return "master-template";
    }

    private void getCaptcha(User user) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        user.setHiddenCaptcha(captcha.getAnswer());
        user.setCaptcha("");
        user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }
}
