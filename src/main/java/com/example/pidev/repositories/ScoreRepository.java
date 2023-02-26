package com.example.pidev.repositories;

import com.example.pidev.entities.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository  extends MongoRepository<Score,Integer> {
}
