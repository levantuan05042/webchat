package org.example.chatservice.model.dto.chatgroup;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatGroupResponse {
    private String name;

    private String createBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}
