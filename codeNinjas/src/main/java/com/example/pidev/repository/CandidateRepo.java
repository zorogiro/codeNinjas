package com.example.pidev.repository;

import com.example.pidev.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface CandidateRepo extends JpaRepository<Candidacy, Integer> {

    String findByTypeCandidacyExists(Integer id);

        Optional<Candidacy> findByStudentAndOffer(User student, Offer offer);
}
