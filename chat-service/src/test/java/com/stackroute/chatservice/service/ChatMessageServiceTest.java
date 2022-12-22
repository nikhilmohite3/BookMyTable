package com.stackroute.chatservice.service;

import com.stackroute.chatservice.exception.ChatNotFoundException;
import com.stackroute.chatservice.model.Chat;
import com.stackroute.chatservice.model.ChatRoom;
import com.stackroute.chatservice.model.MessageStatus;
import com.stackroute.chatservice.repository.ChatRepository;
import com.stackroute.chatservice.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {

    @Mock
    private ChatRepository chatRepository;
    @Mock
    private ChatRoomRepository roomRepository;

    @Mock
    private MongoOperations mongoOperations;
    @Mock
    private ChatRoomService roomService;

    @InjectMocks
    private ChatMessageService chatMessageService;

    @Test
    public void saveData() {
        Chat userChat = new Chat();
        userChat.setUserName("user_test_01");
        userChat.setSenderEmailId("user_test_01@gmail.com");
        userChat.setReceiverEmailId("restaurent_test_01@gmail.com");
        userChat.setChatId("user_test_01@gmail.com_restaurent_test_01@gmail.com");

        when(chatRepository.save(userChat)).thenReturn(userChat);

        assertEquals(userChat, chatMessageService.save(userChat));
    }

    @Test
    public void findMessagesBetweenSenderAndReceiver() throws ChatNotFoundException {
        Chat chat = new Chat();
        chat.setUserName("user_test_03");
        chat.setSenderEmailId("user_test_03@gmail.com");
        chat.setReceiverEmailId("restaurent_test_03@gmail.com");
        chat.setChatId("user_test_03@gmail.com_restaurent_test_03@gmail.com");
        chat.setMessage("THis is a test message ");

        ChatRoom chatRoom = new ChatRoom();
        String chatId = "user_test_03@gmail.com_restaurent_test_03@gmail.com";
        chatRoom.setChatId(chatId);
        chatRoom.setSenderId("user_test_03@gmail.com");
        chatRoom.setReceiverId("restaurent_test_03@gmail.com");


        when(chatRepository.findByChatId(chatId)).thenReturn(List.of(chat));
        when(roomService.getChatId(chatRoom.getSenderId(), chatRoom.getReceiverId(), false)).
                thenReturn(Optional.of(chatId));
        assertEquals(chat.getMessage(),
                chatMessageService.findChatMessages(chat.getSenderEmailId(), chat.getReceiverEmailId()).
                        get(0).getMessage()
        );
    }

    @Test
    public void findMessagesBetweenSenderAndReceiverFailure() throws ChatNotFoundException {
        Chat chat = new Chat();
        chat.setUserName("user_test_03");
        chat.setSenderEmailId("user_test_03@gmail.com");
            chat.setReceiverEmailId("restaurent_test_03@gmail.com");
        chat.setChatId("user_test_03@gmail.com_restaurent_test_03@.com");
        chat.setMessage("THis is a test message ");

        ChatRoom chatRoom = new ChatRoom();
        String chatId = "user_test_03@gmail.com_restaurent_test_03@.com";
        chatRoom.setChatId(chatId);
        chatRoom.setSenderId("user_test_03@gmail.com");
        chatRoom.setReceiverId("restaurent_test_03@gmail.com");


        when(chatRepository.findByChatId(chatId)).thenReturn(List.of());
        when(roomService.getChatId(chatRoom.getSenderId(), chatRoom.getReceiverId(), false)).
                thenReturn(Optional.of(chatId));
        assertThrows(ChatNotFoundException.class, ()-> chatMessageService.findChatMessages(chat.getSenderEmailId(),
                chat.getReceiverEmailId()));
    }

    @Test
    public void findBySenderEmailId() throws ChatNotFoundException {
        Chat chat = new Chat();
        chat.setUserName("user_test_04");
        chat.setIdPrimary("id");
        chat.setSenderEmailId("user_test_04@gmail.com");
        chat.setReceiverEmailId("restaurent_test_04@gmail.com");
        chat.setChatId("user_test_04@gmail.com_restaurent_test_04@gmail.com");
        chat.setMessage("This is a test message ");
        chat.setChatDate(new Date());
        chat.setStatus(MessageStatus.DELIVERED);

        String chatId = chat.getSenderEmailId() + "_" + chat.getReceiverEmailId();

        when(chatRepository.findBySenderEmailId(chat.getSenderEmailId())).
                thenReturn(Optional.of(List.of(chat)));
        assertEquals(chat.getMessage(),
                chatMessageService.findByEmailId(chat.getSenderEmailId()).get(0).getMessage());
    }

    @Test
    public void findBySenderEmailIdFailure() throws ChatNotFoundException {
        when(chatRepository.findBySenderEmailId("abc@gmail.com")).
                thenReturn(Optional.of(List.of()));
        assertThrows(ChatNotFoundException.class,
                () -> chatMessageService.findByEmailId("abc@gmail.com"));
    }

}
