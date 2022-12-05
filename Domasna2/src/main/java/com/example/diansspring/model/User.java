package com.example.diansspring.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "findify_user")
public class User {
    @Id
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String niceName;

    @Column(nullable = false)
    @DateTimeFormat(pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime accountCreationDate;

    public User() {
    }

    public User(String username, String password, String email, String niceName, LocalDateTime accountCreationDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.niceName = niceName;
        this.accountCreationDate = accountCreationDate;
    }
}
