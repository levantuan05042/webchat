package org.example.chatservice.repository;

import org.example.chatservice.model.GroupMessages;
import org.springframework.data.repository.CrudRepository;

public interface GroupMessagesRepository extends CrudRepository<GroupMessages, Long> {
}
