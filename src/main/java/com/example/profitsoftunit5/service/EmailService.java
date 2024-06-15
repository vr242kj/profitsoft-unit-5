package com.example.profitsoftunit5.service;

import com.example.profitsoftunit5.model.entity.Email;
import com.example.profitsoftunit5.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    public void createEmail(String recipientEmail) {
        Email email = new Email();
        email.setSubject("New User Created");
        email.setContent("A new User with email " + recipientEmail + " has been created.");
        email.setRecipients(List.of(recipientEmail));
        email.setStatus("PENDING");
        email.setLastAttemptTime(Instant.now());

        emailRepository.save(email);
        sendEmail(email);
    }

    public void sendEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailSender);
        message.setTo(email.getRecipients().toArray(new String[0]));
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        try {
            javaMailSender.send(message);
            email.setStatus("SENT");
        } catch (Exception e) {
            email.setStatus("FAILED");
            email.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
        }

        email.setLastAttemptTime(Instant.now());
        email.setAttemptCount(email.getAttemptCount() + 1);

        emailRepository.save(email);
    }
}
