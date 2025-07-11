package org.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.example.authservice.jwt.JwtService;
import org.example.authservice.model.User;
import org.example.authservice.model.dto.LoginRequest;
import org.example.authservice.model.dto.RegisterRequest;
import org.example.authservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {
        User user = new User();
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Username đã được dùng...Ahihi đồ ngốc");
        }
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email đã được dùng rồi, đồ ngốc ạ");
        }
        user.setEmail(request.getEmail());
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Số điện thoại đã dùng rồi");
        }
        user.setPhone(request.getPhone());
        user.setFullName(request.getFullName());
        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Username không đúng, có chắc là tạo tài khoản chưa vậy..."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Password sai. Có mỗi password cũng sai, không biết có làm ăn được gì k");
        }
        return jwtService.generateToken(user);
    }

}
