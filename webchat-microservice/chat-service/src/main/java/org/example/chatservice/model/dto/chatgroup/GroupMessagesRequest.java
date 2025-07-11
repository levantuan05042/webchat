package org.example.chatservice.model.dto.chatgroup;

import lombok.Data;

@Data
public class GroupMessagesRequest {
    private  Long groupId;

    private String content;;
}
