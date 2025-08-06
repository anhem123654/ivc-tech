package com.example.ivc_starter.entity.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
