package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Candidacy;
import com.esprit.tn.forum.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CandidacyRepository extends JpaRepository<Candidacy, Integer> {
    @Query("select c from Candidacy c where c.offer.dateExpiration >= ?1 and c.candidate.userId = ?2")
    List<Candidacy> findByOffer_DateExpirationGreaterThanEqualAndCandidate_IdUserEquals(Date dateExpiration, int idUser);

    @Query("select c from Candidacy c where c.offer = ?1")
    List<Candidacy> getCandidaciesByOffer(Offer offer);
}
