package org.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.example.authservice.model.User;
import org.example.authservice.model.dto.UserRequest;
import org.example.authservice.model.dto.UserResponse;
import org.example.authservice.model.dto.UserUpdateRequest;
import org.example.authservice.model.dto.user.SeachFullName;
import org.example.authservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không tìm thấy user"));
    }

    public UserResponse updateByUsername(String username, UserUpdateRequest updatedUser) {
        return userRepository.findByUsername(username).map(user -> {
            user.setFullName(updatedUser.getFullName());

//            if (!user.getEmail().equals(updatedUser.getEmail()) && userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email đã được dùng rồi, đồ ngốc ạ");
//            }
//            user.setEmail(updatedUser.getEmail());


            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank()) {
                if (userRepository.findByEmail(updatedUser.getEmail()).isPresent()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email đã được dùng rồi, đồ ngốc ạ");
                }
                user.setEmail(updatedUser.getEmail());
            }


//            if (!user.getPhone().equals(updatedUser.getPhone()) && userRepository.findByPhone(updatedUser.getPhone()).isPresent()) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, "Số điện thoại đã được dùng rồi, đồ ngốc ạ");
//            }
//            user.setPhone(updatedUser.getPhone());

            if (updatedUser.getPhone() != null && !updatedUser.getPhone().isBlank()) {
                if (userRepository.findByPhone(updatedUser.getPhone()).isPresent()) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Số điện thoại đã được dùng rồi, đồ ngốc ạ");
                }
                user.setPhone(updatedUser.getPhone());
            }

            user.setAvatar(updatedUser.getAvatar());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return mapToResponse(userRepository.save(user));
        }).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không tìm thấy user để cập nhật"));
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không tìm thấy user để xóa"));
        userRepository.delete(user);
    }

    public List<UserResponse> search(UserRequest request) {
        List<UserResponse> result = userRepository.findAll().stream()
                .filter(user -> request.getUsername() == null || user.getUsername().toLowerCase().contains(request.getUsername().toLowerCase()))
                .filter(user -> request.getFullName() == null || user.getFullName().toLowerCase().contains(request.getFullName().toLowerCase()))
                .filter(user -> request.getEmail() == null || user.getEmail().toLowerCase().contains(request.getEmail().toLowerCase()))
                .filter(user -> request.getPhone() == null || user.getPhone().contains(request.getPhone()))
                .filter(user -> request.getRole() == null || user.getRole().equalsIgnoreCase(request.getRole()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Không tìm thấy thông tin nào");
        }

        return result;
    }


    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }
}

