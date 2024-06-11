package com.example.profitsoftunit5.listener;

import com.example.profitsoftunit5.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {
    private final EmailService emailService;

    public EmailListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "email-topic", containerFactory = "kafkaListenerContainerFactory")
    public void listenEmail(String recipientEmail) {
        emailService.createEmail(recipientEmail);
    }
}
