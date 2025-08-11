package com.example.ivc_starter.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ivc_starter.entity.Role;
import com.example.ivc_starter.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoleRepository roleRepo;

    @Override
    public void run(String... args) {
        if (roleRepo.count() == 0) {
            roleRepo.save(new Role(null, "ADMIN"));
            roleRepo.save(new Role(null, "USER"));
            roleRepo.save(new Role(null, "MODERATOR"));
        }
    }
}

