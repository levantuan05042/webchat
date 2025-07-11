package org.example.chatservice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private String sender;

    private String receiver;

    private String content;

    private LocalDateTime timestamp;
}
