package com.trading_company.trading_company.controllers;

import com.trading_company.trading_company.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailService emailService;
    @GetMapping("/send-email/{user-email}")
    public @ResponseBody ResponseEntity sendSimpleEmail(
            @PathVariable("user-email") String email) {
        try {
            emailService.sendSimpleEmail(
                    email,
                    "Уведомление",
                    "Данное письмо сформировано автоматически от сервиса Spring Boot");
        } catch (MailException mailException) {
            return new ResponseEntity<>(
                    "Невозможно отправить почту",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
        return new ResponseEntity<>(
                "Письмо успешно отправлено.",
                HttpStatus.OK
        );
    }
}