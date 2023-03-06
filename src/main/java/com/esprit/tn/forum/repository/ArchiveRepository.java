package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Archive;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveRepository extends MongoRepository<Archive, String> {

}
