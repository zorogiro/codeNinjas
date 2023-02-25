package com.example.pidev.controllers;

import com.example.pidev.entities.*;
import com.example.pidev.services.CandidateCandidacyService;
import com.example.pidev.services.VerificationMoyenne;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/candidacy")
public class CandidateCandidacyController {
    CandidateCandidacyService candidateCandidacyService;
    VerificationMoyenne verificationMoyenne;

    @Transactional
    @PostMapping("/addCandidacy/{idCandidate}/{cinCandidate}/{idOffer}")
    public Candidacy addCandidacy(@PathVariable("idCandidate") int idCandidate,
                                  @PathVariable("idOffer") int idOffer,
                                  @PathVariable("cinCandidate") int cinCandidate,
                                  @RequestBody Candidacy candidacy) {
        return this.candidateCandidacyService.addCandidacy(idCandidate, idOffer, candidacy);
    }

    @Transactional
    @DeleteMapping("/deleteCandidacy/{idCandidacy}")
    public void deleteCandidacy(@PathVariable("idCandidacy") int idCandidacy) {
        this.candidateCandidacyService.deleteCandidacy(idCandidacy);
    }

    @Transactional
    @GetMapping("/getCandidacyById/{idCandidate}/{cin}")
    public List<Candidacy> getCandidacyById(@PathVariable("idCandidate") int idCandidate,
                                            @PathVariable("cin") int cin) {
        return this.candidateCandidacyService.findCandidacyByidCandidate(idCandidate);
    }

    @Transactional
    @GetMapping("/stat/{idCandidate}")
    public StatCandidacy StatCandidacybyIdCandidate(@PathVariable("idCandidate") int idCandidate) {
        return this.candidateCandidacyService.statCandidacyByIdCandidate(idCandidate);
    }

    @GetMapping("/kk")
    public Score verif() throws TikaException, IOException, SAXException, CsvValidationException {

        return verificationMoyenne.calculscore();


    }
}
