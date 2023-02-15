package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String topicName);
}
