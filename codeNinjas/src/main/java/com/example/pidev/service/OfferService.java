package com.example.pidev.service;

import com.example.pidev.entities.Offer;
import com.example.pidev.entities.University;
import com.example.pidev.repository.OfferRepo;
import com.example.pidev.repository.UniversitiesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferService implements IOffer{
    @Autowired
    OfferRepo offerRepo;
    @Autowired
    UniversitiesRepo universitiesRepo;

    @Override
    public Offer addOffer(Offer o ,Integer iduniversitie) {
        //public Offer addOffer(Offer o ) {
        University universities = universitiesRepo.findById(iduniversitie).orElse(null);
         o.setUniversities(universities);
        return offerRepo.save(o);
    }

    @Override
    public Iterable<Offer> retrieveAllOffre() {
        return offerRepo.findAll();
    }

    @Override
    public Offer retrieveOffre(Integer idOffre) {

        return offerRepo.findById(idOffre).get();

    }

    @Override
    public void deleteOffre(Integer id) {
        offerRepo.deleteById(id);
    }

    @Override
    public Offer updateOffre(Offer o) {
        return offerRepo.save(o);
    }


}
