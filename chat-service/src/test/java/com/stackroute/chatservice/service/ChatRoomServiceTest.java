package com.stackroute.chatservice.service;

import com.stackroute.chatservice.model.ChatRoom;
import com.stackroute.chatservice.repository.ChatRoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {

    @Mock
    private ChatRoomRepository roomRepository;

    @InjectMocks
    private ChatRoomService chatRoomService;

    @Test
    public void getChatIdTest() {
        ChatRoom chatRoom = new ChatRoom();
        String chatId = "user_test_03@gmail.com_restaurent_test_03@gmail.com";
        chatRoom.setChatId(chatId);
        chatRoom.setSenderId("user_test_03@gmail.com");
        chatRoom.setReceiverId("restaurent_test_03@gmail.com");

        when(roomRepository.findBySenderIdAndReceiverId(Mockito.anyString(), Mockito.anyString())).
                thenReturn(Optional.of(chatRoom));
        assertEquals(chatRoomService.getChatId(chatRoom.getSenderId(), chatRoom.getReceiverId(),true),
                        Optional.of(chatRoom.getChatId()));
    }

    @Test
    public void getChatIdEmptyTest() {
        when(roomRepository.findBySenderIdAndReceiverId(Mockito.anyString(), Mockito.anyString())).
                thenReturn(Optional.empty());

        assertEquals(Optional.empty(),
                chatRoomService.getChatId(Mockito.anyString(), Mockito.anyString(), false));
    }

    @Test
    public void createChatIdTest() {
        ChatRoom chatRoom = new ChatRoom();
        String chatId = "user_test_03@gmail.com_restaurent_test_03@gmail.com";
        chatRoom.setChatId(chatId);
        chatRoom.setSenderId("user_test_03@gmail.com");
        chatRoom.setReceiverId("restaurent_test_03@gmail.com");
        chatRoom.setIdPrimary("chatRoomId");
        String chatRoomId = chatRoom.getIdPrimary();
        when(roomRepository.findBySenderIdAndReceiverId(Mockito.anyString(), Mockito.anyString())).
                thenReturn(Optional.empty());

        assertEquals(Optional.of(chatId),
                chatRoomService.getChatId(chatRoom.getSenderId(), chatRoom.getReceiverId(), true));
    }
}
