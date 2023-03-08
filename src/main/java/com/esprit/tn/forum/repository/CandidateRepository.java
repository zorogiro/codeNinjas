package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Candidacy;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CandidateRepository extends JpaRepository<Candidacy, Integer> {


    Candidacy findByCandidateAndOffer(User student, Offer offer);

    List<Candidacy> findCandidaciesByOffer(Offer offer);

    List<Candidacy> findCandidaciesByCandidate(User candidate);

}
