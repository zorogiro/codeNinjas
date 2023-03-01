package com.example.pidev.service;

import com.example.pidev.entities.*;
import com.example.pidev.repository.CandidateRepo;
import com.example.pidev.repository.OfferRepo;
import com.example.pidev.repository.UniversitiesRepo;
import com.example.pidev.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class OfferService implements IOffer {
    @Autowired
    OfferRepo offerRepo;
    @Autowired
    UniversitiesRepo universitiesRepo;
    @Autowired
    private UserRepo userRepo;


    @Autowired
    CandidateRepo candidateRepo;
    @Autowired
    CsvService csvReaderService;
    @Override
    public Offer addOffer(Offer o, Integer iduniversitie) {
        //public Offer addOffer(Offer o ) {
        University universities = universitiesRepo.findById(iduniversitie).orElse(null);
        o.setUniversities(universities);
        return offerRepo.save(o);
    }

    @Override
    public List<Offer> retrieveAllOffre() {
        List<Offer> offers = offerRepo.findAll();
        //service authentification
        User student = userRepo.CurrentUser();
        for (Offer offer : offers) {
            offer.setApplied(hasStudentAppliedToOffer(offer.getIdOffer(), student.getIdUser()));
            offer.setTypeOffer(getApplicationStatus(offer.getIdOffer(), student.getIdUser()));
        }

        return hideExpiredOffers(offers);
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

    @Override
    public Map<TypeOffer, Integer> getOfferByType(TypeOffer typeOffer) {
        return offerRepo.countAllByTypeOffer(typeOffer);
    }

    public boolean hasStudentAppliedToOffer(int offerId, int studentId) {
        Offer offer = offerRepo.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        return offer.getCandidacies().stream().anyMatch(candidacy -> Objects
                .equals(candidacy.getCandidate().getIdUser(), studentId));
    }

    public TypeOffer getApplicationStatus(int studentId, int offerId) {
        User student = userRepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Offer offer = offerRepo.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        Candidacy application = candidateRepo.findByStudentAndOffer(student, offer)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        return application.getOffer().getTypeOffer();
    }
    public List<Offer> hideExpiredOffers(List<Offer> offers) {
        List<Offer> validOffers = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Offer offer : offers) {
            if (offer.getDateExpiration().isAfter(now)) {
                validOffers.add(offer);
            }
        }

        return validOffers;
    }

    public double calculateScore(OfferType offerType) throws IOException {


        File filePath = new File("C:\\Users\\khawl\\codeNinjas\\codeNinjas\\src\\main\\resources\\notePI.csv");

        List<CsvRow> csvRows = csvReaderService.readCsvFile(filePath, offerType);

        double score = 0;
        switch (offerType) {
            case sae:
                // TODO: add score formula for software engineering offers

                for (CsvRow row : csvRows) {

                    score = row.getMoy3() * 2 + row.getMoy4() * 2 + row.getGl() * 3 + row.getSpring() * 3 + row.getFr() * 1 + row.getAng() * 2;

                }

                break;
            case ds:
                // TODO: add score formula for data science offers
                for (CsvRow row : csvRows) {

                     score = row.getMoy3() * 2 + row.getMoy4() * 2 + row.getAdf() * 3 + row.getMath() * 3 + row.getFr() * 1 + row.getAng() * 2;

                }
                break;
            case bi:
                // TODO: add score formula for business intelligence offers
                for (CsvRow row : csvRows) {

                    score = row.getMoy3() * 2 + row.getMoy4() * 2 + row.getAdf() * 3 + row.getSpring() * 3 + row.getFr() * 1 + row.getAng() * 2;

                }
                break;
            default:
                throw new IllegalArgumentException("Invalid offer type: " + offerType);
        }

        return score;
    }
    public String compareScores(double calculatedScore,Integer idoffer) {
        Offer offers = offerRepo.findById(idoffer).orElse(null);
        double offerScore = offers.getScoreOffer();
        if (calculatedScore > offerScore) {
            return "Congratulations! You scored higher than the offer score.";
        } else if (calculatedScore == offerScore) {
            return "You scored the same as the offer score.";
        } else {
            return "Sorry, your score is lower than the offer score.";
        }
    }
    public String hideAvailablePlaces(Integer idoffer, List<Candidacy> candidates) {
        int acceptedCandidates = 0;
        Offer offers = offerRepo.findById(idoffer).orElse(null);
        int offernbrplacedispo = offers.getNbrPlaceDisponible();
        for (Candidacy candidate : candidates) {
            if (candidate.getTypeCandidacy() == TypeCandidacy.Accepted) {
                acceptedCandidates++;
            }
        }
        int remainingPlaces = offernbrplacedispo - acceptedCandidates;
        if (remainingPlaces < 0) {
            remainingPlaces = 0;
        }
        return "There are " + remainingPlaces + " places available.";
    }

}
