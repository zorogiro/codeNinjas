package com.example.pidev.repository;


import com.example.pidev.entities.Offer;
import com.example.pidev.entities.TypeCandidacy;
import com.example.pidev.entities.TypeOffer;
import com.example.pidev.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EnumType;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface OfferRepo extends JpaRepository<Offer, Integer> {
    Map<TypeOffer, Integer> countAllByTypeOffer(TypeOffer typeOffer);
    Optional<Offer> findByIdOfferAndCandidaciesContaining(int ifOffer,User student);
}
