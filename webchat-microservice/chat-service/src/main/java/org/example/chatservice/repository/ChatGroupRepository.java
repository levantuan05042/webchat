package org.example.chatservice.repository;

import org.example.chatservice.model.ChatGroup;
import org.springframework.data.repository.CrudRepository;

public interface ChatGroupRepository extends CrudRepository<ChatGroup, Long> {

}
