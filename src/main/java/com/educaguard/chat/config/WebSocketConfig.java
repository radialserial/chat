package com.educaguard.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// Essa classe configura o WebSocket e o protocolo STOMP para permitir comunicação em tempo real.

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 1º - Cliente se conecta ao servidor websocket ao acessar ws://localhost:8080/conect
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        registry.addEndpoint("/conect").setAllowedOrigins("*"); // Configura o endpoint /conect onde os clientes vão se conectar para iniciar a comunicação via WebSocket.
        registry.addEndpoint("/conect") // Configura o endpoint WebSocket
                .setAllowedOrigins("*") // Permite conexões de qualquer origem (ajuste conforme necessário)
                .withSockJS(); // Adiciona suporte a SockJS para fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // prefixo dos endpoints que sempre terão /app/...
        registry.enableSimpleBroker("/chat"); // são canais de mensagens
    }


}
