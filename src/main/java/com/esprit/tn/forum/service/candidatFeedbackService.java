    package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.Feedback;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.CandidateRepository;
import com.esprit.tn.forum.repository.FeedbackRepository;
import com.esprit.tn.forum.repository.OfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class candidatFeedbackService implements IcandidatFeedback {


    FeedbackRepository feedbackRepository;

    OfferRepository offerRepository;
    AuthService authService;


    @Override
    public List<Feedback> retrieveAllFeedback() {
        return null;
    }

    @Override
    public Feedback addcandidatFeedback(Feedback f, Long idoffer) {
        User user = authService.getCurrentUser();
        Offer offer = this.offerRepository.getById(idoffer);
        f.setOffer(offer);
        f.setDateCreation(LocalDateTime.now());
        f.setUser(user);
        f.setStatus("en attente");

        return this.feedbackRepository.save(f);
    }

    @Override
    public Feedback updatecandidatFeedback(Feedback f) {

        f.setStatus("traité");

        return this.feedbackRepository.save(f);
    }

    @Override
    public Feedback updatecandidatFeedbackk(Feedback f) {
        f.setStatus("non traité");
        f.setPriority("il faut le traite dans deux semaines ");
        f.setDateLimite(f.getDateCreation());
        return this.feedbackRepository.save(f);
    }

    @Override
    public void removecandidatFeedback(Long idFeedback) {
        Feedback feedback = this.feedbackRepository.getById(idFeedback);
        System.err.println(feedback.getSubject());
        feedback.setUser(null);
        Feedback ff = this.feedbackRepository.save(feedback);

        this.feedbackRepository.delete(ff);
    }

    @Override
    public double calculerMoyenneReclamationsParUtilisateur() {
        return feedbackRepository.count();
    }

    @Override
    public List<Feedback> getReclamationsByDueDate() {
        return feedbackRepository.findAllByOrderByDateLimiteAsc();
    }


    public void processFeedbacksByPriority() {
        List<Feedback> feedbacks = feedbackRepository.findAllByOrderByPriorityDesc();
        for (Feedback feedback : feedbacks) {
            // traiter le feedback en fonction de sa priorité
        }
    }

    @Configuration
    @EnableScheduling
    public class SchedulerConfig {
        @Autowired

        private candidatFeedbackService candidatFeedbackService;

        @Scheduled(fixedDelay = 60000) // exécuter la méthode toutes les minutes
        public void processFeedbacksByPriority() {
            candidatFeedbackService.processFeedbacksByPriority();
        }
    }


}
