package org.example.authservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.model.User;
import org.example.authservice.model.dto.UserRequest;
import org.example.authservice.model.dto.UserResponse;
import org.example.authservice.model.dto.UserUpdateRequest;
import org.example.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    @PostMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.ok("Đã xóa: " + username);
    }

    @PostMapping("/search")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.search(request));
    }

    @PostMapping("/update/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateByAdmin(@PathVariable String username,
                                      @RequestBody @Valid  UserUpdateRequest user) {
        return userService.updateByUsername(username, user);
    }


}
