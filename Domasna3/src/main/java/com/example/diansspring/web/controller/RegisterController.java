package com.example.diansspring.web.controller;

import cn.apiclub.captcha.Captcha;
import com.example.diansspring.model.User;
import com.example.diansspring.service.UserService;
import com.example.diansspring.util.CaptchaUtil;
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
        model.addAttribute("mainBodyContent", "register");

        User user = new User();
        getCaptcha(user);
        model.addAttribute("captchaDetailsUser", user);

        return "master-template";
    }

    @PostMapping
    public String doRegister(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String repeatPassword,
                             @RequestParam String email,
                             @RequestParam String niceName,
                             @RequestParam String hiddenCaptcha,
                             @RequestParam String captcha) {
        try {
            this.userService.register(username, password, repeatPassword, email, niceName, hiddenCaptcha, captcha);
            return "redirect:/login?successfullyRegistered";
        } catch (Exception exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

    private void getCaptcha(User user) {
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        user.setHiddenCaptcha(captcha.getAnswer());
        user.setCaptcha("");
        user.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }
}
