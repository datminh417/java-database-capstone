package com.project.backend.services; // Cùng package với AppointmentService

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // Giả định bạn có một Secret Key được cấu hình trong application.properties/yml
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    // TIÊU CHÍ 1: Định nghĩa phương thức tạo JWT token bằng email
    public String generateToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail) // Dùng email làm Subject
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // TIÊU CHÍ 2: Implement phương thức trả về signing key từ secret
    private Key getSigningKey() {
        // Giải mã secret key từ Base64
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
