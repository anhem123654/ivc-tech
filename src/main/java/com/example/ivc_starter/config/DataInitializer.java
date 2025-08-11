package com.example.ivc_starter.config;

import com.example.ivc_starter.entity.Role;
import com.example.ivc_starter.entity.User;
import com.example.ivc_starter.repository.RoleRepository;
import com.example.ivc_starter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role moderatorRole = roleRepository.findByName("MODERATOR")
                .orElseGet(() -> roleRepository.save(new Role(null, "MODERATOR")));

        if (userRepository.findByUsername("brucenguyen1852").isEmpty()) {
            User user = new User();
            user.setUsername("brucenguyen1852");
            user.setPassword(passwordEncoder.encode("Anhem@123"));
            user.setRoles(Set.of(moderatorRole));
            userRepository.save(user);
            System.out.println("Default User MODERATOR has been created!");
        }
    }
}
