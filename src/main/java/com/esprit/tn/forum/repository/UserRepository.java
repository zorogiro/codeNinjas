package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.TypeUser;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Query("SELECT COUNT(u) FROM User u WHERE u.typeUser = :role")
    int countByRole(@Param("role") TypeUser role);
    @Query("SELECT COUNT(u) FROM User u WHERE u.typeUser = :typeUser AND u.created >= :startDate AND u.created < :endDate")
    int countByTypeUserAndCreatedDateBetween(@Param("typeUser") TypeUser typeUser, @Param("startDate") Instant startDate, @Param("endDate") Instant endDate);
}
