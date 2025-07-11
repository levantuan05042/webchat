package org.example.chatservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.jwt.JwtService;
import org.example.chatservice.model.GroupMember;
import org.example.chatservice.model.dto.chatgroup.AddMemberRequest;
import org.example.chatservice.service.ChatGroupService;
import org.example.chatservice.service.GroupMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat/member")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> addGroupMember(@RequestHeader("Authorization") String auth,
                                                      @RequestBody AddMemberRequest addMemberRequest) {
        String token = auth.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        groupMemberService.addMember(addMemberRequest.getGroupId(), addMemberRequest.getUsername(), username);
        return ResponseEntity.ok("Thêm thành viên thành công");
    }

}
