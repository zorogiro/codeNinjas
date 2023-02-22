package com.example.pidev.repositories;

import com.example.pidev.entities.Candidacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CandidacyRepository extends JpaRepository<Candidacy, Integer> {
    @Query("select c from Candidacy c where c.offer.dateExpiration >= ?1 and c.candidate.idUser = ?2")
    List<Candidacy> findByOffer_DateExpirationGreaterThanEqualAndCandidate_IdUserEquals(Date dateExpiration, int idUser);
}
