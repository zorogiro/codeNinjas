package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Post;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
