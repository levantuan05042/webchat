package org.example.chatservice.model.dto.chatgroup;

import lombok.Data;

@Data
public class AddMemberRequest {
    private Long groupId;

    private String username;
}
