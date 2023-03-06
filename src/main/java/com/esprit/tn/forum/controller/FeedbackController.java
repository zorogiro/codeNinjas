package com.esprit.tn.forum.controller;


import com.esprit.tn.forum.model.Feedback;
import com.esprit.tn.forum.repository.FeedbackRepository;
import com.esprit.tn.forum.service.IrecruiterFeedbackService;
import com.esprit.tn.forum.service.candidatFeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/feedback/")
public class FeedbackController {

    IrecruiterFeedbackService iFeedbackService;
    candidatFeedbackService icandidatFeedback;
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Transactional
    @PostMapping("addfeedbackCandidacy/{idcandidacy}")
    public Feedback addFeedback(@RequestBody Feedback feedback, @PathVariable("idcandidacy") Long idcandidacy) {
        return iFeedbackService.addFeedback(feedback, idcandidacy);
    }

    @GetMapping("/updatefeedback/{idfeedback}")
    public Feedback updatefeedback(@RequestBody Feedback feedback) {
        feedback.setStatus("traité");
        return iFeedbackService.updateFeedbackrecruiter(feedback);
    }

    @GetMapping("/updatefeedback2/{idfeedback}")
    public Feedback updatefeedback2(@RequestBody Feedback feedback) {
        feedback.setStatus("non traité");
        return iFeedbackService.updateFeedbackrecruiterr(feedback);
    }

    @Transactional
    @DeleteMapping("/removefeedback/{idfeedback}")
    public void removefeedback(@PathVariable("idfeedback") Long idfeedback) {
        icandidatFeedback.removecandidatFeedback(idfeedback);
    }

    @Transactional
    @PostMapping("addcandidateFeedback/{idoffer}")
    public Feedback addcandidatFeedback(@RequestBody Feedback feedback, @PathVariable("idoffer") Long idoffer) {
        return icandidatFeedback.addcandidatFeedback(feedback, idoffer);
    }

    @GetMapping("/updatefeedbackcandidat/{idfeedback}")
    public Feedback updatecandidatFeedback(@RequestBody Feedback feedback) {
        feedback.setStatus("traité");
        return icandidatFeedback.updatecandidatFeedback(feedback);
    }

    @PutMapping("/updatefeedbackcandidat2/{idfeedback}")
    public Feedback updatecandidatFeedbackk(@RequestBody Feedback feedback, @PathVariable String idfeedback) {
        feedback.setStatus("non traité");
        feedback.setPriority("il faut le traite dans deux semaines ");
        feedback.setDateLimite(new Date());
        return icandidatFeedback.updatecandidatFeedbackk(feedback);
    }

    @Transactional
    @GetMapping("/affichage")
    public List<Feedback> getBeneficairesAsC() {
        return iFeedbackService.retrieveAllFeedback();
    }

    @Transactional
    @GetMapping("/affichagereclamation")
    public double calculerMoyenneReclamationsParUtilisateur() {
        return icandidatFeedback.calculerMoyenneReclamationsParUtilisateur();
    }

    @Transactional
    @GetMapping("/stats-by-date")
    public List<FeedbackStats> getFeedbackStatsByDate() {
        List<Object[]> data = feedbackRepository.findFeedbackStatsByDate();
        return data.stream().map(row -> new FeedbackStats((Date) row[0], (Long) row[1], (Double) row[2])).collect(Collectors.toList());
    }

    @GetMapping("/historique")
    public List<Feedback> getHistoriqueReclamationsParMois(@RequestParam int mois) {
        return iFeedbackService.getReclamationsByMonth(mois);
    }

    @GetMapping("/limitdate")
    public List<Feedback> getReclamationsByDueDate() {
        return iFeedbackService.getReclamationsByDueDate();
    }

    // Endpoint pour mettre à jour la date limite d'une réclamation

    public static class FeedbackStats {
        public Date date;
        public long count;
        public double average;


        public FeedbackStats(Date date, long count, double average) {
            this.date = date;
            this.count = count;
            this.average = average;


        }
    }


}









