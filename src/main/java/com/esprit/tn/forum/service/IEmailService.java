package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.EmailDetails;

public interface IEmailService {

    String sendSimpleMail(EmailDetails details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
}
