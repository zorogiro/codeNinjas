package com.example.pidev.repositories;

import com.example.pidev.entities.Score;
import com.example.pidev.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateInformation extends MongoRepository<Score, User> {

}
