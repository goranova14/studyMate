package nl.fontys.s3.studysmate.studymate.controller;

import nl.fontys.s3.studysmate.studymate.domain.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatControllerTest {
    @InjectMocks
    private ChatController chatController;
    @Mock
    private SimpMessagingTemplate simpMessageTemplate;

    @Test
    void sendNotificationToUsers() {
        Message message = new Message();
        Message result = chatController.sendNotificationToUsers(message);
        verify(simpMessageTemplate).convertAndSend("/topic/publicmessages", message);
        assertEquals(message, result);
    }
}