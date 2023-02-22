package com.example.pidev.repositories;

import com.example.pidev.entities.User;
//import com.example.pidev.entities.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryy extends JpaRepository<User, Integer> {
}
