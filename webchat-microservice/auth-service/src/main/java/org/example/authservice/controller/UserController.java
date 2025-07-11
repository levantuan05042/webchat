package org.example.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.jwt.JwtService;
import org.example.authservice.model.User;
import org.example.authservice.model.dto.UserRequest;
import org.example.authservice.model.dto.UserResponse;
import org.example.authservice.model.dto.UserUpdateRequest;
import org.example.authservice.model.dto.user.FullnameRequest;
import org.example.authservice.model.dto.user.SeachFullName;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping
    public UserResponse getMe(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        return userService.getByUsername(username);
    }

    @PostMapping("/update")
    public UserResponse updateMe(@RequestHeader("Authorization") String authHeader,
                                 @RequestBody @Valid UserUpdateRequest user) {
        String token = authHeader.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        return userService.updateByUsername(username, user);
    }

    @GetMapping("/check/{username}")
    public boolean checkExist(@PathVariable String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.search(request));
    }
}
