package com.example.pidev.repositories;

import com.example.pidev.entities.Matiere;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatiereInformation  extends MongoRepository<Matiere,String> {
}
