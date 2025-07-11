package org.example.chatservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.chatservice.jwt.JwtService;
import org.example.chatservice.model.dto.chatgroup.ChatGroupRequest;
import org.example.chatservice.model.dto.chatgroup.ChatGroupResponse;
import org.example.chatservice.service.ChatGroupService;
import org.example.chatservice.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/group")
@RequiredArgsConstructor
public class ChatGroupController {

    private final ChatGroupService chatGroupService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<ChatGroupResponse> create(@RequestHeader("Authorization") String authHeader,
                                                    @RequestBody @Valid ChatGroupRequest chatGroupRequest) {

        String token = authHeader.replace("Bearer ", "");
        String user = jwtService.extractUsername(token);
        return ResponseEntity.ok(chatGroupService.create(chatGroupRequest, user));
    }
}
