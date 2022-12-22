package com.stackroute.chatservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.chatservice.exception.ChatNotFoundException;
import com.stackroute.chatservice.model.Chat;
import com.stackroute.chatservice.service.ChatMessageService;
import com.stackroute.chatservice.service.ChatRoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTest {

    @Autowired
    private ChatController chatController;

    @MockBean
    private ChatMessageService messageService;
    @MockBean
    private ChatRoomService chatRoomService;
    @MockBean
    private SimpMessagingTemplate template;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chatController).build();
    }


    @Test
    void findChatMessageTestByEmailSuccess() throws Exception {
        String userEmailId = "user_01@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();

        Chat chat = new Chat();
        chat.setUserName("user_01");
        chat.setSenderEmailId("user_01@gmail.com");
        chat.setReceiverEmailId("rest_01@gmail.com");
        chat.setMessage("This is a test message");

        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        when(messageService.findByEmailId(userEmailId)).thenReturn(chatList);
        mockMvc.perform(get("/chat/user_01@gmail.com").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(chatList))).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void findChatMessageTestByEmailFailure() throws Exception {
        String userEmailId = "user_01@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();

        Chat chat = new Chat();
        chat.setUserName("user_01");
        chat.setSenderEmailId("user_01@gmail.com");
        chat.setReceiverEmailId("rest_01@gmail.com");
        chat.setMessage("This is a test message");

        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        when(messageService.findByEmailId(any())).
                thenThrow(ChatNotFoundException.class);
        mockMvc.perform(get("/chat/user_01@gmail.com").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(chatList))).
                andDo(print()).andExpect(status().isNotFound()).
                andExpect(content().string(equalTo("User Messages Not Found")));
    }

    @Test
    void findChatMessageByUserAndReceiverEmailSuccess() throws Exception {
        String senderEmailId = "user_01@gmail.com";
        String receiverEmailId = "test_01@restaurent.com";

        Chat chat = new Chat();
        chat.setUserName("user_01");
        chat.setSenderEmailId("user_01@gmail.com");
        chat.setReceiverEmailId("test_01@gmail.com");
        chat.setMessage("This is a test message");

        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);
        ObjectMapper objectMapper = new ObjectMapper();
        when(messageService.findChatMessages(senderEmailId, receiverEmailId)).thenReturn(chatList);
        mockMvc.perform(get("/chat/user_01@gmail.com/test_01@restauremt.com").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.
                                writeValueAsString(chatList))).
                andDo(print()).
                andExpect(status().isOk());
    }

    @Test
    void findChatMessageByUserAndReceiverEmailFailure() throws Exception {
        String senderEmailId = "user_01@gmail.com";
        String receiverEmailId = "test_01@restaurent.com";

        Chat chat = new Chat();
        chat.setUserName("user_01");
        chat.setSenderEmailId("user_01@gmail.com");
        chat.setReceiverEmailId("test_01@gmail.com");
        chat.setMessage("This is a test message");

        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);
        ObjectMapper objectMapper = new ObjectMapper();
        when(messageService.findChatMessages(any(), any())).
                thenThrow(ChatNotFoundException.class);
        mockMvc.perform(get("/chat/user_01@gmail.com/test_01@restauremt.com").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.
                                writeValueAsString(chatList))).
                andDo(print()).
                andExpect(status().isNotFound()).
                andExpect(content().string(equalTo("User Messages Not Found")));
    }
}
