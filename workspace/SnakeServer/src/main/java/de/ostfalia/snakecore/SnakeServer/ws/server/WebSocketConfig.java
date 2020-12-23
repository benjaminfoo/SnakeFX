package de.ostfalia.snakecore.SnakeServer.ws.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static de.ostfalia.snakecore.ProjectEndpoints.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(STOMP_BROKER_TOPIC);
        config.setApplicationDestinationPrefixes(STOMP_APP_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
         registry.addEndpoint(STOMP_MESSAGE_MAPPING_CHAT);
         registry.addEndpoint(STOMP_MESSAGE_MAPPING_CHAT).withSockJS();
    }


}