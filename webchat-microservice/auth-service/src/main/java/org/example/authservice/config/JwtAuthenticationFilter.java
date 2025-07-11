package org.example.authservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.authservice.jwt.JwtService;
import org.example.authservice.security.CustomUserDetails;
import org.example.authservice.model.User;
import org.example.authservice.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); //Lấy header Authorization từ request.

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        } //nếu k có || k bắt đầu bằng "Bearer " thì bỏ qua

        String token = authHeader.substring(7); //Lấy phần JWT token ra (bỏ từ "Bearer ").

        //giải mã token
        String username;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        String role = jwtService.extractRole(token);
        System.out.println("ROLE from JWT: " + role); // Kiểm tra thử


        //Nếu đã có username và chưa có ai đăng nhập
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Tìm người dùng theo username từ DB. Trả về null nếu không tìm thấy.
            User user = userRepository.findByUsername(username)
                    .orElse(null);

            if (user != null) {
                UserDetails userDetails = new CustomUserDetails(user); //tạo một UserDetails để Spring hiểu đây là người dùng đã đăng nhập.
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
