package com.example.pidev.controller;


import com.example.pidev.entities.Feedback;

import com.example.pidev.repository.FeedbackRepository;
import com.example.pidev.repository.UserRepository;
import com.example.pidev.services.IrecruiterFeedbackService;
import com.example.pidev.services.candidatFeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/feedback/")
public class FeedbackController {

    IrecruiterFeedbackService iFeedbackService;
    candidatFeedbackService icandidatFeedback;


    @Transactional
    @PostMapping("addfeedbackCandidacy/{iduser}/{cin}/{idcandidacy}")
    public Feedback addFeedback(@RequestBody Feedback feedback,

                                @PathVariable("iduser") int iduser,
                                @PathVariable("cin") int cin,

                                @PathVariable("idcandidacy") int idcandidacy
    ) {


        return iFeedbackService.addFeedback(feedback, iduser, cin, idcandidacy);

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
    public void removefeedback(@PathVariable("idfeedback") int idfeedback) {


        icandidatFeedback.removecandidatFeedback(idfeedback);

    }

    @Transactional
    @PostMapping("addcandidateFeedback/{idoffer}/{iduser}/{cin}")
    public Feedback addcandidatFeedback(@RequestBody Feedback feedback,

                                        @PathVariable("idoffer") int idoffer,
                                        @PathVariable("cin") int cin,
                                        @PathVariable("iduser") int iduser
    ) {


        return icandidatFeedback.addcandidatFeedback(feedback, idoffer, iduser, cin);

    }


    @GetMapping("/updatefeedbackcandidat/{idfeedback}")
    public Feedback updatecandidatFeedback(@RequestBody Feedback feedback) {

        feedback.setStatus("traité");
        return icandidatFeedback.updatecandidatFeedback(feedback);

    }

    @GetMapping("/updatefeedbackcandidat2/{idfeedback}")
    public Feedback updatecandidatFeedbackk(@RequestBody Feedback feedback) {

        feedback.setStatus("non traité");
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


    @Autowired
    private FeedbackRepository feedbackRepository;

    @Transactional
    @GetMapping("/stats-by-date")
    public List<FeedbackStats> getFeedbackStatsByDate() {
        List<Object[]> data = feedbackRepository.findFeedbackStatsByDate();
        return data.stream()
                .map(row -> new FeedbackStats((Date) row[0], (Long) row[1], (Double) row[2]))
                .collect(Collectors.toList());
    }

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


    @GetMapping("/historique")
    public List<Feedback> getHistoriqueReclamationsParMois(@RequestParam int mois) {

        return iFeedbackService.getReclamationsByMonth(mois);
    }




}









