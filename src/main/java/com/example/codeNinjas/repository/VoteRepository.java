package com.example.codeNinjas.repository;

import com.example.codeNinjas.model.Post;
import com.example.codeNinjas.model.User;
import com.example.codeNinjas.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

}
