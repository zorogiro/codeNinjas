package com.example.pidev.controllers;

import com.example.pidev.entities.Offer;
import com.example.pidev.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Offre")
@AllArgsConstructor

public class OffreController {
    @Autowired
    OfferService offerService;
    @GetMapping("/")

    public Iterable<Offer>  GetAllOffre(){
        return offerService.retrieveAllOffre();
    }

    @GetMapping("/retrieve-Offre/{Offre-id}")

    public Offer retrieveOffre(@PathVariable("Offre-id") Integer OffreId) {
        return offerService.retrieveOffre(OffreId);
    }

    @PostMapping("/addOffre/{idUniv}")
    @ResponseBody

    public void addOffre(@RequestBody Offer o, @PathVariable("idUniv") Integer idUniv ) {

        offerService.addOffer(o,idUniv);
    }

    @PutMapping("/updateOffre")
    @ResponseBody
    public void updatePOffre(@RequestBody Offer offre) {
        offerService.updateOffre(offre);
    }

    @DeleteMapping("/deleteOffre/{Offre-id}")
    @ResponseBody
    public  Iterable<Offer> deleteEtudiant(@PathVariable("Offre-id") Integer OffreId ) {

        offerService.deleteOffre(OffreId);
        return offerService.retrieveAllOffre();

    }


}
