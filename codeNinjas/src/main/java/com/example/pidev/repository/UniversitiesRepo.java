package com.example.pidev.repository;


import com.example.pidev.entities.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversitiesRepo extends JpaRepository<University, Integer> {
      University getByIdUniversity(Integer idUniversity);

}
