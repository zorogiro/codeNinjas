package com.esprit.tn.forum.repository;


import com.esprit.tn.forum.dto.OfferDto;
import com.esprit.tn.forum.model.Candidacy;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.TypeOffer;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Map<TypeOffer, Long> countOfferByTypeOffer(TypeOffer typeOffer);

    List<Offer> findAllByTypeOffer(TypeOffer typeOffer);
    List<Offer> findAllById(Iterable<Long> longs);

    List<OfferDto> findByDateExpirationBefore(LocalDateTime now);

    List<Offer> findByDateExpirationAfter(LocalDateTime now);




}
