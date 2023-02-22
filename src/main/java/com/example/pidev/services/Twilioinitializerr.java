package com.example.pidev.services;

import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class Twilioinitializerr {

    private final Twilioproperties twilioproperties;

    public Twilioinitializerr(Twilioproperties twilioproperties)
    {
        this.twilioproperties=twilioproperties;
        Twilio.init("houssem.hassani@esprit.tn","Esprit2021-2022@", "AC522e7bcbe6182a9e25d41e420bd2603f");
        System.out.println("twilio intialized with account: "+twilioproperties.getAccountSid());
    }

}