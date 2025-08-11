package com.example.ivc_starter.controller;

import com.example.ivc_starter.config.JwtUtil;
import com.example.ivc_starter.entity.Role;
import com.example.ivc_starter.entity.User;
import com.example.ivc_starter.entity.dto.AuthResponse;
import com.example.ivc_starter.entity.dto.LoginRequest;
import com.example.ivc_starter.repository.RoleRepository;
import com.example.ivc_starter.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {
        Role r = roleRepository.findByName(role.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of(r));

        userRepository.save(user);
        return "User registered successfully";
    }

    @Operation(summary = "Đăng nhập và lấy JWT Token")
    @PostMapping("/login")
    public AuthResponse login(
            @RequestParam String username,
            @RequestParam String password) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                        .build());

        return new AuthResponse(token);
    }
}
