package org.example.lattice.controller;

import lombok.RequiredArgsConstructor;
import org.example.lattice.DTO.LoginRequest;
import org.example.lattice.DTO.RegisterRequest;
import org.example.lattice.Repository.UserRepository;
import org.example.lattice.models.User;
import org.example.lattice.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User user = User.builder()
                .email(req.email())
                .password(passwordEncoder.encode(req.Password()))
                .name(req.name())
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("token", jwtService.generateToken(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(()->new RuntimeException("User Not found"));
        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return ResponseEntity.ok(Map.of("token", jwtService.generateToken(user)));
    }

}
