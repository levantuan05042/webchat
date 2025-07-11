package org.example.chatservice.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "demo_groupmember")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

//thành viên
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Long groupId;

    private String username;

    private String role = "ROLE_MEMBER";

    private LocalDateTime joineAt = LocalDateTime.now();
}
