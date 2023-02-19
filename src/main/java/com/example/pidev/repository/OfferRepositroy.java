package com.example.pidev.repository;

import com.example.pidev.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepositroy extends JpaRepository<Offer,Integer> {
}
