package nl.fontys.s3.studysmate.studymate.business.impl;

import nl.fontys.s3.studysmate.studymate.domain.Response;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CreateEmailUseCaseImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private CreateEmailUseCaseImpl createEmailUseCase;

    @Test
    void sendEmailPositive(){
        String subject ="grade";
        String to="denicaz@abv.bg";
        String text="Grade change has been made";
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("denicagoranova@abv.bg");
        message.setText(text);
        message.setTo(to);
        message.setSubject(subject);
        Response expected= Response.builder().message("Successfully").build();
        Mockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        Response actual=createEmailUseCase.sendEmail(subject,to,text);
        assertEquals(expected,actual);

    }

}