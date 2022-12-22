package com.stackroute.chatservice.repository;

import com.stackroute.chatservice.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    List<Chat> findByChatId(String chatId);
    Optional<List<Chat>> findBySenderEmailId(String senderEmailId);
}
