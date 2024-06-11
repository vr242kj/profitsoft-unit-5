package com.example.profitsoftunit5.service;

import com.example.profitsoftunit5.model.entity.Email;
import com.example.profitsoftunit5.repository.EmailRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class FailedEmailService {

    private final EmailRepository emailRepository;
    private final EmailService emailService;

    public FailedEmailService(EmailRepository emailRepository, EmailService emailService) {
        this.emailRepository = emailRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 300000)
    public void retryFailedEmails() {
        List<Email> failedEmails = emailRepository.findByStatus("FAILED");
        failedEmails.forEach(emailService::sendEmail);
    }
}
