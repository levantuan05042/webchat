package org.example.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.jwt.JwtService;
import org.example.chatservice.model.dto.MessageRequest;
import org.example.chatservice.model.dto.MessageResponse;
import org.example.chatservice.model.dto.user.FullnameRequest;
import org.example.chatservice.model.dto.user.SeachFullName;
import org.example.chatservice.model.dto.user.UserRequest;
import org.example.chatservice.model.dto.user.UserResponse;
import org.example.chatservice.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final JwtService jwtService;

    @PostMapping
    public MessageResponse sendMessage(@RequestHeader("Authorization") String authHeader,
                                       @RequestBody MessageRequest messageRequest
    ) {
        String token = authHeader.replace("Bearer ", "");
        String sender = jwtService.extractUsername(token);

        return messageService.send(sender, messageRequest);
    }

    @PostMapping("/{receiver}")
    public List<MessageResponse> history(@RequestHeader("Authorization") String authHeader,
                                         @PathVariable String receiver) {
        String token = authHeader.replace("Bearer ", "");
        String sender = jwtService.extractUsername(token);
        return messageService.getHistory(sender, receiver);
    }
    @PostMapping("/search")
    public ResponseEntity<List<UserResponse>> search(@RequestBody UserRequest request) {
        return ResponseEntity.ok(messageService.searchFullNames(request));
    }
}
