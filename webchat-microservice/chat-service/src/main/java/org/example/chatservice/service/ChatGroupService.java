package org.example.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.model.ChatGroup;
import org.example.chatservice.model.GroupMember;
import org.example.chatservice.model.Message;
import org.example.chatservice.model.dto.MessageResponse;
import org.example.chatservice.model.dto.chatgroup.ChatGroupRequest;
import org.example.chatservice.model.dto.chatgroup.ChatGroupResponse;
import org.example.chatservice.repository.ChatGroupRepository;
import org.example.chatservice.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGroupService {

    private final ChatGroupRepository chatGroupRepository;
    private final GroupMemberRepository groupMemberRepository;

    private ChatGroupResponse toResponse(ChatGroup chatGroup) {
        ChatGroupResponse chatGroupResponse = new ChatGroupResponse();
        chatGroupResponse.setName(chatGroup.getName());
        chatGroupResponse.setCreateBy(chatGroup.getCreateBy());
        chatGroupResponse.setCreatedAt(chatGroup.getCreatedAt());
        return chatGroupResponse;
    }

    public ChatGroupResponse create(ChatGroupRequest chatGroupRequest, String sender ) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setName(chatGroupRequest.getGroupName());
        chatGroup.setCreateBy(sender);
        ChatGroup save = chatGroupRepository.save(chatGroup);

        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(chatGroup.getId());
        groupMember.setUsername(sender);
        groupMember.setRole("ROLE_LEADER");
        groupMemberRepository.save(groupMember);
        return toResponse(save);
    }



}
