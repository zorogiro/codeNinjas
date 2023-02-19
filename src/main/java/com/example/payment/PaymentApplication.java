package com.example.payment;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PaymentApplication {

@PostConstruct
public void setup(){

    Stripe.apiKey="sk_test_51KsyziEmiVl8fQHylCw9rCjbEc5Nf6A1PYYrgL36UHb9PoqUWBBEEHCV3NeF6fi2wdadkYX2HzqUQobNYBvJeGFc00LrVKSNXf";


}

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }

}
