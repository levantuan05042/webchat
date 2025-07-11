package org.example.authservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String fullName;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "^(08\\d{8}|09\\d{8})$", message = "Số điện thoại phải bắt đầu bằng 08 hoặc 09 và có 10 chữ số")
    private String phone;

    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    private String avatar;

}
