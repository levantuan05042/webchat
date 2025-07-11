package org.example.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.jwt.JwtService;
import org.example.chatservice.model.dto.MessageRequest;
import org.example.chatservice.model.dto.MessageResponse;
import org.example.chatservice.model.dto.chatgroup.GroupMessagesRequest;
import org.example.chatservice.model.dto.chatgroup.GroupMessagesResponse;
import org.example.chatservice.service.GroupMessagesService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/groupmessage")
@RequiredArgsConstructor
public class GroupMessagesController {

    private final GroupMessagesService groupMessagesService;
    private final JwtService jwtService;
    @PostMapping
    public GroupMessagesResponse sendGroupMessage(@RequestHeader("Authorization") String authHeader,
                                             @RequestBody GroupMessagesRequest groupMessagesRequest
    ) {
        String token = authHeader.replace("Bearer ", "");
        String sender = jwtService.extractUsername(token);
        return groupMessagesService.sendgroup(sender, groupMessagesRequest.getGroupId(), groupMessagesRequest);
    }
}
