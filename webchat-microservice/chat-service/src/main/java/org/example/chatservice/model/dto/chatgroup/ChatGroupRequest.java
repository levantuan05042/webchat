package org.example.chatservice.model.dto.chatgroup;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChatGroupRequest {
    @NotBlank(message = "Không được để trống tên nhóm")
    private String groupName;
}
