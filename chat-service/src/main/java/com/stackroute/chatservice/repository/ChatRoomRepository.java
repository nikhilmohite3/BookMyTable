package com.stackroute.chatservice.repository;

import com.stackroute.chatservice.model.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderEmailId, String recipientEmailId);
}
