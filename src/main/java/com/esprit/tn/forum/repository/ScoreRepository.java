package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository  extends MongoRepository<Score,Integer> {
    Score findScoreByCinUser(String cin);
}
