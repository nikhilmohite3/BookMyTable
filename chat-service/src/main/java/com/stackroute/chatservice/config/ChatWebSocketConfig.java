package com.stackroute.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatWebSocketConfig implements WebSocketMessageBrokerConfigurer {
    /**
     * registers /ws STOMP endpoint. This endpoint is used by the client to connect to the STOMP server.
     * It also enables the SockJS fallback options, so that alternative messaging options
     * may be used if WebSockets are not available.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-endpoint").setAllowedOriginPatterns("*").withSockJS();
    }

    /**
     * Configures a simple in-memory message broker with one destination for sending and receiving messages,
     * this destination is prefixed with /user,
     * it also designates the /app prefix for messages that are bound for methods annotated with @MessageMapping
     *
     * User destination prefix /user is used by ConvertAndSendToUser method of
     * SimpleMessagingTemplate to prefix all user-specific destinations with /user.
     *
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app");

        /** "/chatroom" for the public messages & "/user" for private messages */
        config.enableSimpleBroker("/chatroom", "/user");

        config.setUserDestinationPrefix("/user");
    }
}
