package org.example.authservice.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.example.authservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey secretKey; //Biến chứa khóa bí mật dùng để ký và xác thực token JWT

    //Biến secretKey sẵn sàng dùng cho việc ký và xác minh JWT sau này.
    @PostConstruct // hàm này sẽ chạy sau khi Spring inject xong tất cả biến
    public void init() {
        secretKey = new SecretKeySpec(jwtSecret.getBytes(),
                SignatureAlgorithm.HS256.getJcaName()); //tạo khóa đối xứng (HS256) từ chuỗi bí mật jwtSecret
    }

    //Tạo token
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername()) //trường chuẩn dùng để lưu định danh, lưu trong subject
                .claim("role", user.getRole()) //lưu trong claim
                .claim("email", user.getEmail())
                .claim("phone", user.getPhone())
                .claim("avatar", user.getAvatar())
                .claim("fullName", user.getFullName())
                .setIssuedAt(new Date()) //thời điểm tạo token
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // hạn token 1 ngày
                .signWith(secretKey) //ký token nhằm đảm bảo tính toàn vẹn và xác thực.
                .compact(); //Chuyển token thành một chuỗi JWT hoàn chỉnh gồm 3 phần:Header.Payload.Signature
    }

    public String extractUsername(String token) {
        System.out.println("token: " + token);
        return Jwts.parserBuilder() //tạo đối tượng xây dựng để phân tích JWT.
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //giả mã fullname
    public String extractRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public String extracPhone(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("phone", String.class);
    }


}
