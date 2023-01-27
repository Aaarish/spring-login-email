package com.example.springloginemail.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private Integer userId;
    private String name;
    private String email;
    private String password;
}
