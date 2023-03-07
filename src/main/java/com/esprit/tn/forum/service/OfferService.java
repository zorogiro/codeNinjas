package com.esprit.tn.forum.service;

import com.esprit.tn.forum.dto.OfferDto;
import com.esprit.tn.forum.mapper.OfferMapper;
import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.CandidateRepository;
import com.esprit.tn.forum.repository.OfferRepository;
import com.esprit.tn.forum.repository.UniversityRepository;
import com.esprit.tn.forum.repository.UserRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class OfferService {
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UniversityRepository universityRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    public AuthService authService;
    @Autowired
    private OfferMapper offerMapper;
    @Autowired
    private CsvService csvService;




    public Offer addOffer(Offer offer, Long iduniv) {
        User user = authService.getCurrentUser();
        if (!user.isCandidate()){
        University university = universityRepository.getUniversityByIdUniversity(iduniv);
        offer.setRecruiter(user);
        offer.setUniversity(university);
        offer.setDateCreation(LocalDateTime.now());
        offer.setDateExpiration(LocalDateTime.now().plusDays(15));
        return offerRepository.save(offer);}

        return null;
    }





//    @Transactional(readOnly = true)
//    public List<OfferDto> getAllOffers() {
//        Long userId = authService.getCurrentUser().getUserId();
//        List<Offer> offers = offerRepository.findAll();
//        List<OfferDto> offerDtos = new ArrayList<>();
//        for (Offer offer : offers) {
//            OfferDto offerDto = offerMapper.toDto(offer);
//            offerDto.setApplied(hasStudentAppliedToOffer(offer.getIdOffer(), userId));
//            offerDto.setTypeOffer(getApplicationStatus(offer.getIdOffer()));
//            offerDtos.add(offerDto);
//        }
//        return offerDtos;
//    }

    //get the offers that a student has applied to
    @Transactional(readOnly = true)
    public List<OfferDto> getStudentOffers(){
        User student =authService.getCurrentUser();
        List<Candidacy> candidacies = candidateRepository.findCandidaciesByCandidate(student);
        List<OfferDto> offerDtos = new ArrayList<>();
        for (Candidacy candidacy: candidacies) {
            OfferDto offerDto = offerMapper.toDto(candidacy.getOffer());
            offerDtos.add(offerDto);
        }

        return offerDtos;
    }

    //verfy if the student has applied to the offer
    public boolean isApplied(Offer offer) {
        User student = authService.getCurrentUser();
        List<Candidacy> candidacies = candidateRepository.findCandidaciesByCandidate(student);
        for( Candidacy candidacy : candidacies){
            if (candidacy.getOffer().equals(offer)){
                return true;
            }
        }
        return false;
    }




    @Transactional(readOnly = true)
    public List<OfferDto> getAllOffer(){
        List<Offer> offers = offerRepository.findAll();
        return offers.stream().map(offerMapper::toDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public OfferDto getOffer(Long id){
        Offer offer = offerRepository.findById(id).get();
        return offerMapper.toDto(offer);
    }

    public void deleteOffre(Long id) {
        if (!authService.getCurrentUser().isCandidate()) {
            offerRepository.deleteById(id);
        }
    }


    public Offer updateOffre(Offer o) {
        if (!authService.getCurrentUser().isCandidate()) {
            return offerRepository.save(o);
        }
        return null;
    }
//
//    public Map<TypeOffer, Long> getNOfferByType(TypeOffer typeOffer) {
//        return offerRepository.countOfferByTypeOffer(typeOffer);
//    }


    public List<OfferDto> getOfferByType(TypeOffer typeOffer){
        List<Offer> offers = offerRepository.findAllByTypeOffer(typeOffer);
        List<OfferDto> offerDtos = offers.stream()
                .map(offerMapper::toDto)
                .collect(Collectors.toList());
        return offerDtos;
    }


//    public boolean hasStudentAppliedToOffer(Long offerId, Long studentId) {
//        Offer offer = offerRepository.findById(offerId)
//                .orElseThrow(() -> new RuntimeException("Offer not found"));
//
//        return offer.getCandidacies().stream().anyMatch(candidacy -> Objects
//                .equals(candidacy.getCandidate().getUserId(), studentId));
//    }

    public TypeOffer getApplicationStatus(Long offerId) {
        User student = authService.getCurrentUser();
        Offer offer = offerRepository.findById(offerId).get() ;
        Candidacy application = candidateRepository.findByCandidateAndOffer(student, offer);
        return application.getOffer().getTypeOffer();
    }

    public List<OfferDto> getValidOffers() {
        LocalDateTime now = LocalDateTime.now();
        return offerRepository.findByDateExpirationBefore(now);
    }

    public List<OfferDto> getAvailableOffer() {
        // get the current date and time
        LocalDateTime now = LocalDateTime.now();
        // query the database to retrieve the valid offers
        List<Offer> validOffers = offerRepository.findByDateExpirationAfter(now);
        // map the entities to DTOs
        List<OfferDto> validOfferDtos = validOffers.stream()
                .map(offerMapper::toDto)
                .collect(Collectors.toList());
        // return the list of valid offers
        return validOfferDtos;
    }


    int getNbrOfAvailablePlaces(Offer offer){
        List<Candidacy> candidacies =candidateRepository.findCandidaciesByOffer(offer);
        for (Candidacy candidacy: candidacies) {
            if (offer.getNbrPlaceDisponible() != 0) {
                if (candidacy.getTypeCandidacy() == TypeCandidacy.Accepted) {
                    offer.setNbrPlaceDisponible(offer.getNbrPlaceDisponible() - 1);
                }
            }
        }

        return offer.getNbrPlaceDisponible();
    }

    public double getScore(TypeOffer typeOffer) throws CsvValidationException, IOException {

        List<CsvRow> csvRows = csvService.readCsv();
        User user = authService.getCurrentUser();

        Optional<CsvRow> optionalCsvRow = csvRows.stream()
                .filter(csvRow -> csvRow.getUserId() == user.getUserId())
                .findFirst();

        if (optionalCsvRow.isPresent()) {
            CsvRow csvRow = optionalCsvRow.get();
            double score = 0;

            switch (typeOffer) {
                case DS:
                    score = (csvRow.getMath() + csvRow.getPython() + csvRow.getAdf() + csvRow.getSpring()) / 4;
                    break;
                case BI:
                    score = (csvRow.getMath() + csvRow.getPython() + csvRow.getAdf() + csvRow.getSpring() + csvRow.getGl()) / 5;
                    break;
                case SAE:
                    score = (csvRow.getMoy3() + csvRow.getMoy4() + csvRow.getAng() + csvRow.getFr()) / 4;
                    break;
                case GC:
                    score = (csvRow.getMath() + csvRow.getPython() + csvRow.getAdf() + csvRow.getSpring() + csvRow.getGl() + csvRow.getFr() + csvRow.getAng()) / 7;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid offer type: " + typeOffer);
            }

            return score;
        } else {
            return -1;
        }
    }

}
