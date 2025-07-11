package org.example.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.model.GroupMessages;
import org.example.chatservice.model.Message;
import org.example.chatservice.model.dto.MessageRequest;
import org.example.chatservice.model.dto.MessageResponse;
import org.example.chatservice.model.dto.chatgroup.GroupMessagesRequest;
import org.example.chatservice.model.dto.chatgroup.GroupMessagesResponse;
import org.example.chatservice.repository.GroupMemberRepository;
import org.example.chatservice.repository.GroupMessagesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GroupMessagesService {
    private final GroupMessagesRepository groupMessagesRepository;
    private final GroupMemberRepository groupMemberRepository;

    private GroupMessagesResponse toResponse(GroupMessages groupMessages) {
        GroupMessagesResponse groupMessagesResponse = new GroupMessagesResponse();
        groupMessagesResponse.setSender(groupMessages.getSendUsername());
        groupMessagesResponse.setGroupId(String.valueOf(groupMessages.getGroupId()));
        groupMessagesResponse.setContent(groupMessages.getContent());
        groupMessagesResponse.setSendAt(groupMessages.getSendAt());
        return groupMessagesResponse;
    }

    public GroupMessagesResponse sendgroup(String sender, Long groupId, GroupMessagesRequest groupMessagesRequest) {

        if (groupMessagesRequest.getGroupId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Để trống nhóm nhận thì gửi cho ai");
        }

        if (!groupMemberRepository.findByGroupIdAndUsername(groupId, sender).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Bạn không ở nhóm này..Ahihi đồ ngốc");
        }

        GroupMessages groupMessages = GroupMessages.builder()
                .sendUsername(sender)
                .groupId(groupMessagesRequest.getGroupId())
                .content(groupMessagesRequest.getContent())
                .sendAt(LocalDateTime.now())
                .build();
        GroupMessages save = groupMessagesRepository.save(groupMessages);
        return toResponse(save);
    }
}
