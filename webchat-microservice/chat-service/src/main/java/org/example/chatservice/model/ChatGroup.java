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

@Data
@Entity(name = "demo_chatgroup")
@Builder
@AllArgsConstructor
@NoArgsConstructor

//Thông tin nhóm
public class ChatGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String createBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}
