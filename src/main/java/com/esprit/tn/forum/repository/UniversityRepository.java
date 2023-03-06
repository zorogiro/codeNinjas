package com.esprit.tn.forum.repository;


import com.esprit.tn.forum.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
    University getUniversityByIdUniversity(Long idUniv);

}
