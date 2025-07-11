package org.example.chatservice.model.dto.chatgroup;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupMessagesResponse {
    private String sender;

    private String groupId;

    private String content;

    private LocalDateTime sendAt;
}
