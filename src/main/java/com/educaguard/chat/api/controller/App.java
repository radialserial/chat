package com.educaguard.chat.api.controller;

import com.educaguard.chat.api.dto.MsgDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("*")
public class App {

    // 2º - Cliente envia uma mensagem para o endpoint /app/chatmessage
    @MessageMapping("/chatMessage")
    @SendTo("/chat")
    public MsgDTO sendMessage(MsgDTO message) {
        return message; // 3º Mensagem será enviada para todos os clientes inscritos em /chat
    }


}
