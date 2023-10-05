package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.configuration.isauthenticated.IsAuthenticated;
import nl.fontys.s3.studysmate.studymate.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessageTemplate;
    @IsAuthenticated
    @PostMapping
    public Message sendNotificationToUsers(@RequestBody Message message) {
        simpMessageTemplate.convertAndSend("/topic/publicmessages", message);
        return message;
    }

}
