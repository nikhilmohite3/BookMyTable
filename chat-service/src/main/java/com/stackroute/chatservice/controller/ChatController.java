package com.stackroute.chatservice.controller;

import com.stackroute.chatservice.exception.ChatNotFoundException;
import com.stackroute.chatservice.model.Chat;
import com.stackroute.chatservice.service.ChatMessageService;
import com.stackroute.chatservice.service.ChatRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ChatController {

    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final Logger Log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat")
    @SendTo("/chatroom/public")
    // whenever user sends msg to "/app/chat", then this method will be called.
    // Whenever a message is received to this method, we need to send this to a topic(broadcast) which is "/chatroom/public".
    // Whenever a user wants to receive a message from this public chatroom, user should listen to this topic
    public Chat sendChat(@Payload Chat chatMessage) {
       return chatMessage;
    }

    @MessageMapping("/private-message/{channelId}")
    // Whenever a user sends a message to /app/private-message, then this method is called
    public Chat sendPrivateMessage(@RequestBody Chat chatMessage, @DestinationVariable String channelId) {
        System.out.println("senderEmail: " +chatMessage.getSenderEmailId());
        System.out.println("ReceiverEmail: " +chatMessage.getReceiverEmailId());

        Optional<String> chatId = chatRoomService.getChatId(
                chatMessage.getSenderEmailId(),
                chatMessage.getReceiverEmailId(),
                true);
        System.out.println("Chat Id generated: "+ chatId.get());

        chatMessage.setChatId(chatId.get());
        chatMessageService.save(chatMessage);

        // Whenever user wants to listen to this private msg, he needs to listen at url
        // /user/{receiverEmailId}/private
        simpMessagingTemplate.convertAndSend(
                "/private/" + channelId,
                chatMessage
        );

        Log.debug("Sending Message { " + chatMessage.getMessage() + " } to receiver = "
                        + chatMessage.getReceiverEmailId());
        return chatMessage;
    }

    @GetMapping("/chat/{senderEmailId}/{receiverEmailId}")
    public ResponseEntity<?> findChatMessages(@PathVariable String senderEmailId,
                                              @PathVariable String receiverEmailId) {
        try {
            List<Chat> chatMessages = chatMessageService.findChatMessages(senderEmailId, receiverEmailId);
            return ResponseEntity.ok("Messages: \n" + chatMessages);
        }catch (ChatNotFoundException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("User Messages Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chat/{senderEmailId}")
    public ResponseEntity<?> findUserMessage(@PathVariable String senderEmailId) {
        try {
            List<Chat> userMessages = chatMessageService.findByEmailId(senderEmailId);
            return ResponseEntity.ok("User Messages: \n"+ userMessages);
        } catch (ChatNotFoundException exception) {
            exception.printStackTrace();
            return new ResponseEntity<>("User Messages Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
