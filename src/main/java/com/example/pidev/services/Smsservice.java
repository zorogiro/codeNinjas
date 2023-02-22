package com.example.pidev.services;

import com.example.pidev.entities.Smsrequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@Service
public class Smsservice {

    private final Twilioproperties twilioproperties;

    @Autowired
    public Smsservice(Twilioproperties twilioproperties)
    {
        this.twilioproperties=twilioproperties;
    }

    //send message to number
    public String sendsms(Smsrequest smsrequest)
    {

        Message message=Message.creator(new PhoneNumber("+21653376376"), new PhoneNumber("+12679532590"),"dffsfs").create();
        return message.getStatus().toString();


    }
}
