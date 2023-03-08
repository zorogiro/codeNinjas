package com.esprit.tn.forum.controller;

import com.esprit.tn.forum.dto.OfferDto;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.TypeOffer;
import com.esprit.tn.forum.repository.OfferRepository;
import com.esprit.tn.forum.service.CsvService;
import com.esprit.tn.forum.service.OfferService;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/offre")
@AllArgsConstructor
@Controller
public class OffreController {

    private final OfferService offerService;
    private final OfferRepository offerRepository;
    private final CsvService csvService;

    @GetMapping
    public List<OfferDto> getAllOffers() {
        return offerService.getAllOffer();
    }

    @GetMapping("/retrieve-Offre/{OffreId}")
    public OfferDto retrieveOffre(@PathVariable Long OffreId) {
        return offerService.getOffer(OffreId);
    }

    @PostMapping("/add/{idUniv}")
    public void addOffre(@RequestBody Offer offer, @PathVariable("idUniv") Long idUniv) {
        offerService.addOffer(offer, idUniv);
    }

    @PreAuthorize("hasRole('Recruiter')")
    @PutMapping
    public void updatePOffre(@RequestBody Offer offre) {
        offerService.updateOffre(offre);
    }

    @PreAuthorize("hasRole('Recruiter')")
    @DeleteMapping("/deleteOffre/{Offre-id}")
    public void deleteOffer(@PathVariable("Offre-id") Long OffreId) {
        offerService.deleteOffre(OffreId);
    }

    @GetMapping("/byTypeOffer/{typeOffer}")
    public List<OfferDto> getAllTypeOffer(@PathVariable("typeOffer") TypeOffer typeOffer) {
        return offerService.getOfferByType(typeOffer);

    }

    @GetMapping("/available")
    public List<OfferDto> getAvailableOffer() {
        return offerService.getAvailableOffer();

    }

    @GetMapping("/score/{typeOffer}")
    public double getScore(@PathVariable TypeOffer typeOffer) throws IOException, CsvValidationException {
        return offerService.getScore(typeOffer);

    }

    @GetMapping("/studentOffer")
    public List<OfferDto> getStudentOffers() {
        return offerService.getStudentOffers();

    }

    @GetMapping("stat/{typeOffer}")
    public Map<TypeOffer, Long> getNOfferByType(@PathVariable("typeOffer") TypeOffer typeOffer){
        return offerService.getNOfferByType(typeOffer);
    }




}
