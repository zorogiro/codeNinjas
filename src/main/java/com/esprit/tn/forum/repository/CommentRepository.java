package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Comment;
import com.esprit.tn.forum.model.Post;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);

    int countByUser(User user);
}
