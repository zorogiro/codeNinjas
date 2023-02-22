package com.example.pidev;

import com.stripe.Stripe;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PidevApplication {



    public static void main(String[] args) {
        SpringApplication.run(PidevApplication.class, args);
    }

}
