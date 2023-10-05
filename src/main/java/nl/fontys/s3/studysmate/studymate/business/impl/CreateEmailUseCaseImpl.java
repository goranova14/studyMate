package nl.fontys.s3.studysmate.studymate.business.impl;

import lombok.AllArgsConstructor;
import nl.fontys.s3.studysmate.studymate.business.CreateEmailUseCase;
import nl.fontys.s3.studysmate.studymate.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CreateEmailUseCaseImpl implements CreateEmailUseCase {
    @Autowired
    private JavaMailSender emailSender;


    @Override
    public Response sendEmail(String subject, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        return Response.builder().message("Successfully").build();
    }
}

