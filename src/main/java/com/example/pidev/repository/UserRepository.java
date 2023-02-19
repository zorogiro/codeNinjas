package com.example.pidev.repository;

import com.example.pidev.entities.User;
import com.example.pidev.entities.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UserKey> {
}
