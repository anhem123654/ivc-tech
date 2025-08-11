package com.example.ivc_starter.entity.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Set<String> roles; // ["ADMIN", "USER"]
}