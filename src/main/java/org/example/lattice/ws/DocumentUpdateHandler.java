package org.example.lattice.ws;

import lombok.RequiredArgsConstructor;
import org.example.lattice.models.DocumentUpdateMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import tools.jackson.databind.ObjectMapper;

@Controller
@RequiredArgsConstructor
public class DocumentUpdateHandler {

    private final RedisTemplate<String , String> redisTemplate;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;

    @MessageMapping("/document.update")
    public void updateDocument(DocumentUpdateMessage message){

        // save to db later

        // publish to redis
        redisTemplate.convertAndSend("workspace" + message.workspaceId() + "docs" ,
                objectMapper.writeValueAsString(message)
        );

    }


}