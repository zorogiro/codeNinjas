package com.example.pidev.controllers;

import com.example.pidev.entities.Candidacy;
import com.example.pidev.services.RecuiterCandidacyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping( "/recuiterCandidacy")
public class RecuiterCandidacyController {

    RecuiterCandidacyService recuiterCandidacyService;

    @PostMapping("/makeCandidacyOnProcessing/{idcandidacy}")
    @Transactional
    public Candidacy makeCandidacyOnProcessing(@PathVariable("idcandidacy") int idcandidacy){
        return this.recuiterCandidacyService.makeCandidacyOnProcessing(idcandidacy);
    }
    @PostMapping("/makeCandidacyAcepted/{idcandidacy}")
    @Transactional
    public Candidacy makeCandidacyAcepted(@PathVariable("idcandidacy") int idcandidacy){
        Candidacy candidacy=this.recuiterCandidacyService.makeCandidacyAccepted(idcandidacy);
        if( candidacy!=null)
            return new Candidacy();
        return candidacy;
    }
    @PostMapping("/makeCandidacyRefused/{idcandidacy}")
    @Transactional
    public Candidacy makeCandidacyRefused(@PathVariable("idcandidacy") int idcandidacy){
        return this.recuiterCandidacyService.makeCandidacyRefused(idcandidacy);
    }
    @GetMapping("/showCandidacyByidOffre/{idOffre}")
    @Transactional
    public List<Candidacy> showCandidacyByidOffre(@PathVariable("idOffre") int idOffre){
        return this.recuiterCandidacyService.showCandidacyByidOffre(idOffre);
    }
}
