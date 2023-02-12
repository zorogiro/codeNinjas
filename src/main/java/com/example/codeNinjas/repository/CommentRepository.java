package com.example.codeNinjas.repository;

import com.example.codeNinjas.model.Comment;
import com.example.codeNinjas.model.Post;
import com.example.codeNinjas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
