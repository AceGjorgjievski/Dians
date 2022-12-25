package com.example.diansspring.config;

import com.example.diansspring.model.exceptions.CaptchaDoesNotMatchException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class CustomAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String hiddenCaptcha = httpRequest.getParameter("hiddenCaptcha");
        String captcha = httpRequest.getParameter("captcha");

        if (hiddenCaptcha == null) {
            chain.doFilter(request, response);
        }
        else if (!hiddenCaptcha.equals(captcha)) {
            httpResponse.sendRedirect("/login?error=Invalid Captcha");
        }
        else {
            chain.doFilter(request, response);
        }
    }
}
