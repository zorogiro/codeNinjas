package com.example.pidev.repository;

import com.example.pidev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo  extends JpaRepository<User, Integer> {
}
