package com.example.pidev;

import com.example.pidev.entities.Score;
import com.example.pidev.entities.User;
import com.example.pidev.repositories.CandidateInformation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class PidevApplication  {
    private  static CandidateInformation candidateInformation;
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PidevApplication.class, args);


    }


}
