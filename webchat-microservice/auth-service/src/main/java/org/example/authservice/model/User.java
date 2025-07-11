package org.example.authservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "demo_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String avatar;
}

