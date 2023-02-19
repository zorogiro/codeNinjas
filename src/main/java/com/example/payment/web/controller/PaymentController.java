package com.example.payment.web.controller;

import com.example.payment.dto.CreatePayment;
import com.example.payment.dto.CreatePaymentResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {


    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                        .setAmount(15 * 100l)
                        .setCurrency("usd")
                         .build();

        // Create a PaymentIntent with the order amount and currency

        PaymentIntent intent = PaymentIntent.create(createParams);
       return new CreatePaymentResponse(intent.getClientSecret());

    }
}

