package com.example.springloginemail.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthResponse {
    private String jwt;
}
