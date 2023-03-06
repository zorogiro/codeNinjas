package com.esprit.tn.forum.controller;

import com.esprit.tn.forum.dto.OfferDto;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.TypeOffer;
import com.esprit.tn.forum.service.CsvService;
import com.esprit.tn.forum.service.OfferService;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/offre")
@AllArgsConstructor
@Controller
public class OffreController {

    private final OfferService offerService;
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

    @PutMapping
    public void updatePOffre(@RequestBody Offer offre) {
        offerService.updateOffre(offre);
    }

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
//
//    @GetMapping("/compareScores/{idoffer}")
//    public String compareScores(@PathVariable Long idoffer) throws IOException {
//        return offerService.compareScores(idoffer);
//    }

//    @GetMapping("/hideAvailablePlaces/{idoffer}")
//    public String hideAvailablePlaces(@PathVariable Long idoffer, List<Candidacy> candidates) throws IOException {
//        return offerService.hideAvailablePlaces(idoffer, candidates);
//    }
}
