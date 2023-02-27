package com.example.pidev;

import com.example.pidev.entities.Score;
import com.example.pidev.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class PidevApplication{
    @Autowired
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PidevApplication.class, args);


    }




}
