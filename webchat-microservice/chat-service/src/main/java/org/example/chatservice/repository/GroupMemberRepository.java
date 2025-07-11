package org.example.chatservice.repository;

import org.example.chatservice.model.GroupMember;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupMemberRepository extends CrudRepository<GroupMember, Long> {

    Optional<GroupMember> findByGroupIdAndUsername(Long groupId, String username);
}
