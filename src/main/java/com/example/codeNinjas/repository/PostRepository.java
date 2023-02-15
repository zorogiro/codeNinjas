package com.example.codeNinjas.repository;

import com.example.codeNinjas.model.Post;
import com.example.codeNinjas.model.Topic;
import com.example.codeNinjas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic(Topic topic);

    List<Post> findByUser(User user);
}
