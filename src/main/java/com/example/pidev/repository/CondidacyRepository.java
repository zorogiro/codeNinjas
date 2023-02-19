package com.example.pidev.repository;

import com.example.pidev.entities.Candidacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondidacyRepository extends JpaRepository<Candidacy,Integer> {
}
