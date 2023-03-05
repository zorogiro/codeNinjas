package com.example.pidev.services;

import com.example.pidev.entities.EmailDetails;

public interface IEmailService {

    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
