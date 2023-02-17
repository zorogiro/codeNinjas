package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Post;
import com.esprit.tn.forum.model.Topic;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTopic(Topic topic);

    List<Post> findByUser(User user);

    List<Post> findAllByDeleted(boolean deleted);

    List<Post> findAllByDeletedTrue();
}
