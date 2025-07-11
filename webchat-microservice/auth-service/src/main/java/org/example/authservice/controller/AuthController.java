package org.example.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.model.User;
import org.example.authservice.model.dto.LoginRequest;
import org.example.authservice.model.dto.RegisterRequest;
import org.example.authservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        String token = authService.register(request);
        return ResponseEntity.ok().body("Đăng ký thành công. Token là: " + token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok().body("Đăng nhập thành công. Token là: " + token);
    }
}

