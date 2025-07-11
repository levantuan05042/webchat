package org.example.authservice.security;

import org.example.authservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lấy quyền từ user (ở đây là 1 role duy nhất)
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // dùng để xác thực nếu login bằng username-password
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // được Spring gọi khi cần tên đăng nhập
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // có thể check trạng thái hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // có thể thêm logic khóa tài khoản
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // có thể thêm logic token hết hạn
    }

    @Override
    public boolean isEnabled() {
        return true; // có thể thêm logic kích hoạt
    }

    public User getUser() {
        return user;
    }
}
