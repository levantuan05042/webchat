package org.example.chatservice.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String username;

    private String fullName;

    private String email;

    private String phone;

    private String role;
}
