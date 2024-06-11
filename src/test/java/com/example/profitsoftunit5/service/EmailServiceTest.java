package com.example.profitsoftunit5.service;

import com.example.profitsoftunit5.ProfitsoftUnit5Application;
import com.example.profitsoftunit5.listener.EmailListener;
import com.example.profitsoftunit5.model.entity.Email;
import com.example.profitsoftunit5.repository.EmailRepository;
import com.example.profitsoftunit5.testcontainers.TestElasticConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ContextConfiguration(classes = {ProfitsoftUnit5Application.class, TestElasticConfig.class})
public class EmailServiceTest {

    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private EmailListener emailListener;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        Email email = new Email();
        email.setId("1");
        email.setSubject("New User Created");
        email.setContent("A new User with email test@example.com has been created.");
        email.setRecipients(List.of("test@example.com"));
        email.setStatus("PENDING");
        email.setAttemptCount(0);
        email.setLastAttemptTime(null);

        emailRepository.save(email);
    }

    @AfterEach
    void clear() {
        emailRepository.deleteAll();
    }

    @Test
    void sendEmail_shouldChangeStatusToSent() {
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        Optional<Email> emailOpt = emailRepository.findById("1");
        assertTrue(emailOpt.isPresent());

        Email email = emailOpt.get();
        emailService.sendEmail(email);

        Optional<Email> byId = emailRepository.findById("1");
        assertTrue(byId.isPresent());

        Email updatedEmail = byId.get();

        assertEquals("1", updatedEmail.getId());
        assertEquals("SENT", updatedEmail.getStatus());
        assertEquals(1, updatedEmail.getAttemptCount());
        assertNotNull(updatedEmail.getLastAttemptTime());
        assertNull(updatedEmail.getErrorMessage());
    }

    @Test
    void sendEmail_shouldChangeStatusToFailed() {
        doThrow(RuntimeException.class).when(javaMailSender).send(any(SimpleMailMessage.class));
        Optional<Email> emailOpt = emailRepository.findById("1");
        assertTrue(emailOpt.isPresent());

        Email email = emailOpt.get();
        emailService.sendEmail(email);

        Optional<Email> byId = emailRepository.findById("1");
        assertTrue(byId.isPresent());

        Email updatedEmail = byId.get();

        assertEquals("1", updatedEmail.getId());
        assertEquals("FAILED", updatedEmail.getStatus());
        assertEquals(1, updatedEmail.getAttemptCount());
        assertNotNull(updatedEmail.getLastAttemptTime());
        assertNotNull(updatedEmail.getErrorMessage());
    }
}
