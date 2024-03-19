package fr.vitalitte.vitalittebackend.email.rest;

import fr.vitalitte.vitalittebackend.payload.request.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class EmailController {
    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/api/email")
    public void sendEmail(@RequestBody EmailRequest emailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("teach2home69@gmail.com");
        message.setTo(emailBody.getTo());
        message.setSubject(emailBody.getSubject());
        message.setText(emailBody.getMessage());
        mailSender.send(message);
    }
}
