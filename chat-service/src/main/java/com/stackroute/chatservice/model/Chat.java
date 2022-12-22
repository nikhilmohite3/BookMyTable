package com.stackroute.chatservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "chats")
public class Chat {
    @Id
    private String idPrimary;
    private String chatId;
    private String userName;
    private String senderEmailId;
    private String receiverEmailId;
    private String message;
    private MessageStatus status;
    private Date chatDate;
}
