package com.example.diansspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/home", true)
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("FINDIFY_COOKIE")
                        .logoutSuccessUrl("/login")
                )
                .exceptionHandling((excp) -> excp
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);

        return authenticationManagerBuilder.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(AuthenticationManagerBuilder auth) {
//        UserDetails user =
//                User.builder()
//                        .username("admin")
//                        .password(passwordEncoder.encode("admin"))
//                        .roles("ADMIN")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}
