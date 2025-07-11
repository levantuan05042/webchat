package org.example.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.client.UserClient;
import org.example.chatservice.model.GroupMember;
import org.example.chatservice.repository.GroupMemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final UserClient userClient;

    public void addMember(Long groupId, String username, String addedByUsername) {

        if (!groupMemberRepository.findByGroupIdAndUsername(groupId, addedByUsername).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Bạn không thuộc nhóm này...");
        }

        if (!userClient.exists(username)){
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Người này khum tồn tại..Ahihi đồ ngốc");
        }
        if (groupMemberRepository.findByGroupIdAndUsername(groupId, username).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Nhìn lại đi...Người này trong nhóm rồi");
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setUsername(username);
        groupMember.setRole("ROLE_MEMBER / Người thêm: " + addedByUsername);
        groupMemberRepository.save(groupMember);
    }
}
