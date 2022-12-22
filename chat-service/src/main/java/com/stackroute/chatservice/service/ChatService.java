package com.stackroute.chatservice.service;

import com.stackroute.chatservice.exception.ChatNotFoundException;
import com.stackroute.chatservice.model.Chat;
import com.stackroute.chatservice.model.MessageStatus;

import java.util.List;

public interface ChatService {
    Chat save(Chat chatMessage);
    List<Chat> findChatMessages(String senderEmailId, String recipientEmailId) throws ChatNotFoundException;
    void notifyChatStatus(String senderId, String recipientId, MessageStatus messageStatus);
    List<Chat> findByEmailId(String senderEmailId) throws ChatNotFoundException;
}
