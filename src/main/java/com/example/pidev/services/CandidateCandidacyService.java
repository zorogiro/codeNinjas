package com.example.pidev.services;

import com.example.pidev.entities.*;
import com.example.pidev.repositories.CandidacyRepository;
import com.example.pidev.repositories.OfferRepository;
import com.example.pidev.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CandidateCandidacyService implements ICandidateCandidacyService{
    @Autowired
    CandidacyRepository candidacyRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Candidacy addCandidacy(int idCandidate, int idOffer,Candidacy candidacy) {

        User user=this.userRepository.findById(idCandidate).get();
        System.out.println(user.getTypeUser());
        Offer offer=this.offerRepository.findById(idOffer).get();
        System.out.println(offer.getScoreOffer());
        if(user.getTypeUser().equals(TypeUser.Candidate))
        {
            candidacy.setCandidate(user);
            candidacy.setTypeCandidacy(TypeCandidacy.OnHold);
            candidacy.setOffer(offer);
            return this.candidacyRepository.save(candidacy);
        }
        return null;
    }

    @Override
    public void deleteCandidacy(int idCandidacy) {

        this.candidacyRepository.deleteById(idCandidacy);
        System.err.println("Candidacy deleted");
    }

    @Override
    public List<Candidacy> findCandidacyByidCandidate(int idCandidate) {
        List<Candidacy> candidacyList=this.candidacyRepository.findAll();
        List<Candidacy> candidacies=new ArrayList<Candidacy>();
        User user= this.userRepository.findById(idCandidate).get();
        for(Candidacy c : candidacyList){
            if(c.getCandidate().equals(user) && user.getTypeUser().equals(TypeUser.Candidate))            {
                candidacies.add(c);
            }
        }
        return candidacies;
    }

    @Override
    public StatCandidacy statCandidacyByIdCandidate(int idCandidate) {
        System.err.println(idCandidate);
        List<Candidacy> candidacies= candidacyRepository.findAll();
        User candidate=userRepository.findById(idCandidate).get();
        StatCandidacy statCandidacies=new StatCandidacy(candidate,0,0,0,0,0);
        statCandidacies.setCandidate(candidate);
        for(Candidacy c:candidacies){
            if(c.getCandidate().equals(candidate)){
                if (c.getTypeCandidacy().equals(TypeCandidacy.Accepted)) {
                    int nbr=statCandidacies.getNbrCandidacyAccepted();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyAccepted(nbr);
                }
                if (c.getTypeCandidacy().equals(TypeCandidacy.Refus)) {
                    int nbr=statCandidacies.getNbrCandidacyRefused();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyRefused(nbr);
                }
                if (c.getTypeCandidacy().equals(TypeCandidacy.Processing)) {
                    int nbr=statCandidacies.getNbrCandidacyProcessing();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyProcessing(nbr);
                }
                if (c.getTypeCandidacy().equals(TypeCandidacy.OnHold)) {
                    int nbr=statCandidacies.getNbrCandidacyOnHold();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyOnHold(nbr);
                }
                int nbr=statCandidacies.getNbrCandidacy();
                nbr=nbr+1;
                statCandidacies.setNbrCandidacy(nbr);
            }
        }
        return statCandidacies;
    }

    @Override
    public List<Candidacy> findCandidacyForOfferExpired(int idCandidate) {

        User candidate= userRepository.findById(idCandidate).get();
        List<Candidacy> candidacies=candidacyRepository.findAll();
        Date date= java.sql.Date.valueOf(new LocalDate().toString());
        List<Candidacy> candidaciesFinal=new ArrayList<>();
        if(candidacies!=null)
        for(Candidacy c:candidacies){
            if(c.getCandidate().equals(candidate))
            {
                System.err.println((c.getOffer().getDateExpiration().getDate()-date.getDate()));
                if((c.getOffer().getDateExpiration().getDate()- date.getDate()<=5)){
                    candidaciesFinal.add(c);
                }

            }
        }
        return candidaciesFinal;

    }

}
