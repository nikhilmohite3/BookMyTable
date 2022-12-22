package com.stackroute.chatservice.service;

import com.stackroute.chatservice.model.ChatRoom;
import com.stackroute.chatservice.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(String senderEmailId, String recipientEmailId, boolean doesExists) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderEmailId, recipientEmailId)
                .map(ChatRoom::getChatId)
                .or(()-> {
                    if(!doesExists) {
                        return Optional.empty();
                    }
                    var chatId = String.format("%s_%s", senderEmailId, recipientEmailId);
                    ChatRoom sender = ChatRoom.builder().idPrimary(chatId).chatId(chatId).senderId(senderEmailId).
                            receiverId(recipientEmailId).build();
                    System.out.println(sender);
                    chatRoomRepository.save(sender);
                    return Optional.of(chatId);
                });
    }
}
