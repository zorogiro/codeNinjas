package com.example.pidev.repository;

import com.example.pidev.entities.Housing;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HousingRepository extends JpaRepository<Housing, Integer> {

}
