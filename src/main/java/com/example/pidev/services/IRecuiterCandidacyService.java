package com.example.pidev.services;

import com.example.pidev.entities.Candidacy;
import com.example.pidev.entities.Offer;
import com.example.pidev.entities.StatistiquesOffer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IRecuiterCandidacyService {
    public Candidacy makeCandidacyOnProcessing(int candidacy);
    public Candidacy makeCandidacyAccepted(int candidacy);
    public Candidacy makeCandidacyRefused(int candidacy);
    public List<Candidacy> showCandidacyByidOffre(int idOffre);
    public Map<Offer,StatistiquesOffer> showNumberCandidacyByOffer();
}
