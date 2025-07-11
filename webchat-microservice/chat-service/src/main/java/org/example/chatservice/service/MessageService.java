package org.example.chatservice.service;

import lombok.RequiredArgsConstructor;
import org.example.chatservice.client.UserClient;
import org.example.chatservice.jwt.JwtService;
import org.example.chatservice.model.Message;
import org.example.chatservice.model.dto.MessageRequest;
import org.example.chatservice.model.dto.MessageResponse;
import org.example.chatservice.model.dto.user.FullnameRequest;
import org.example.chatservice.model.dto.user.SeachFullName;
import org.example.chatservice.model.dto.user.UserRequest;
import org.example.chatservice.model.dto.user.UserResponse;
import org.example.chatservice.repository.MessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserClient userClient;
    private final JwtService jwtService;

    private MessageResponse toResponse(Message message) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSender(message.getSender());
        messageResponse.setReceiver(message.getReceiver());
        messageResponse.setContent(message.getContent());
        messageResponse.setTimestamp(message.getTimestamp());
        return messageResponse;
    }

    public MessageResponse send(String sender, MessageRequest messageRequest) {

        if (messageRequest.getReceiver() == null || messageRequest.getReceiver().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Để trống người nhận thì gửi cho ai");
        }
        if (messageRequest.getReceiver().equals(sender)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Tại sao lại gửi cho chính mình");
        }

        if (!userClient.exists(messageRequest.getReceiver())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Người nhận không tồn tại..Ahihi đồ ngốc");
        }

        Message message = Message.builder()
                .sender(sender)
                .receiver(messageRequest.getReceiver())
                .content(messageRequest.getContent())
                .timestamp(LocalDateTime.now())
                .build();
        Message save = messageRepository.save(message);
        return toResponse(save);
    }

    public List<MessageResponse> getHistory(String user1, String user2) {
//        if (!userRepository.findByUsername(user2).isPresent()) {
//            throw new ResponseStatusException(
//                    HttpStatus.UNAUTHORIZED, "Username không tồn tại..Ahihi đồ ngốc");
//        }
        if (!userClient.exists(user2)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Người này không tồn tại..Ahihi đồ ngốc");
        }

        List<MessageResponse> history = messageRepository
                .findBySenderAndReceiverOrReceiverAndSender(user1, user2, user2, user1)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        if (history.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Đã nhắn gì với người ta đâu. Làm ăn sống quá");
        }
        return history;
    }

    public List<UserResponse> searchFullNames(UserRequest request) {
        return userClient.search(request);
    }
}
