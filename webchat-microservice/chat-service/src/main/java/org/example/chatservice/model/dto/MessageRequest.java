package org.example.chatservice.model.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String receiver;

    private String content;
}
