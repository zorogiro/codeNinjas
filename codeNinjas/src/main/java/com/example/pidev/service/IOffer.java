package com.example.pidev.service;

import com.example.pidev.entities.Offer;
import com.example.pidev.entities.TypeOffer;

import java.util.List;
import java.util.Map;

public interface IOffer {

    public Offer addOffer(Offer o, Integer iduniversitie);

    //public Offer addOffer (Offer o);
    public Iterable<Offer> retrieveAllOffre();

    Offer retrieveOffre(Integer idOffre);

    public void deleteOffre(Integer id);

    Offer updateOffre(Offer o);

    public Map<TypeOffer, Integer> getOfferByType(TypeOffer typeOffer);

    public List<Offer> hideExpiredOffers(List<Offer> offers);
}