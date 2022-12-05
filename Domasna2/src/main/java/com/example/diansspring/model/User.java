package com.example.diansspring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Entity
public class User {
    @Id
    private String username;
    private String password;
    private String email;
    private String niceName;
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
