package com.stackroute.chatservice.service;

import com.stackroute.chatservice.exception.ChatNotFoundException;
import com.stackroute.chatservice.model.Chat;
import com.stackroute.chatservice.model.MessageStatus;
import com.stackroute.chatservice.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService implements ChatService{
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private ChatRoomService chatRoomService;

    public Chat save(Chat chatMessage) {
        chatRepository.save(chatMessage);
        return chatMessage;
    }

    public List<Chat> findChatMessages(String senderEmailId, String recipientEmailId) throws ChatNotFoundException{
        Optional<String> chatId = chatRoomService.getChatId(senderEmailId, recipientEmailId, false);
        System.out.println("Chat ID : "+ chatId.get());

        var messages = chatId.map(id -> chatRepository.findByChatId(id)).
                orElse(new ArrayList<>());
        System.out.println("Messages of "+ senderEmailId + ": "+ messages);

        if(messages.size() > 0) {
            notifyChatStatus(senderEmailId, recipientEmailId, MessageStatus.DELIVERED);
            return messages;
        }
        throw new ChatNotFoundException("Chat not found between sender: "+ senderEmailId +
                                " and receiver" + recipientEmailId);
    }
    public void notifyChatStatus(String senderId, String recipientId, MessageStatus messageStatus) {
        Query query = new Query(
                Criteria
                        .where("senderEmailId").is(senderId)
                        .and("receiverEmailId").is(recipientId));
        Update update = Update.update("chatStatus", messageStatus);
        mongoOperations.updateMulti(query, update, Chat.class);
    }

    public List<Chat> findByEmailId(String senderEmailId) throws ChatNotFoundException{
        Optional<List<Chat>> chats = chatRepository.findBySenderEmailId(senderEmailId).
                map(chatList -> {
                    chatList.
                        stream().
                        map(chat -> {
                            chat.setStatus(MessageStatus.DELIVERED);
                            return chatRepository.save(chat);
                        }
                );
                    return new ArrayList<>(chatList);
        });

        System.out.println("Chat Messages: " + chats.get());
        if(chats.get().size() == 0) {
            throw new ChatNotFoundException("Chat does not exists for user: "+ senderEmailId);
        }
        return chats.get();
    }

}
