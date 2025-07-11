package org.example.chatservice.repository;

import org.example.chatservice.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrReceiverAndSender(String sender1, String receiver1, String sender2, String receiver2);
}
