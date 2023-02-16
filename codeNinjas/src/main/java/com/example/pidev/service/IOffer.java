package com.example.pidev.service;

import com.example.pidev.entities.Offer;
public interface IOffer {

    public Offer addOffer (Offer o, Integer iduniversitie);
    //public Offer addOffer (Offer o);
    public Iterable<Offer> retrieveAllOffre();
    Offer retrieveOffre(Integer idOffre);

    public void deleteOffre(Integer id);
    Offer updateOffre(Offer o);
}
