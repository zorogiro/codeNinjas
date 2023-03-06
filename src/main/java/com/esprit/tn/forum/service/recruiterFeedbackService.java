package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.Candidacy;
import com.esprit.tn.forum.model.Feedback;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.CandidateRepository;
import com.esprit.tn.forum.repository.FeedbackRepository;
import com.esprit.tn.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class recruiterFeedbackService implements IrecruiterFeedbackService {

    FeedbackRepository feedbackRepository;
    CandidateRepository condidacyRepository;
    UserRepository userRepository;
    AuthService authService;


    @Override
    public List<Feedback> retrieveAllFeedback() {

        return feedbackRepository.findAll();

    }

    @Override
    public Feedback addFeedback(Feedback f, Long idcondidacy) {

        User user = authService.getCurrentUser();
        if (!user.isCandidate()) {
            f.setUser(user);
            Candidacy candidacy = condidacyRepository.findById(idcondidacy).get();
            f.setCandidacy(candidacy);
            f.setStatus("en attente");
            return this.feedbackRepository.save(f);
        }
        throw new RuntimeException("u don't have access");
    }

    @Override
    public Feedback updateFeedbackrecruiter(Feedback f) {
        return this.feedbackRepository.save(f);
    }

    @Override
    public Feedback updateFeedbackrecruiterr(Feedback f) {
        return this.feedbackRepository.save(f);
    }

    @Override
    public Feedback removeFeedback(Long idFeedback) {
        feedbackRepository.deleteById(idFeedback);
        return null;
    }


    public void processFeedbacksByPriority() {
        List<Feedback> feedbacks = feedbackRepository.findAllByOrderByPriorityDesc();
        for (Feedback feedback : feedbacks) {
            // traiter le feedback en fonction de sa priorité
            System.err.println(feedback.getSubject());
        }
    }

    @Configuration
    @EnableScheduling
    public class SchedulerConfig {
        @Autowired
        private recruiterFeedbackService recruiterFeedbackService;

        @Scheduled(fixedDelay = 30000) // exécuter la méthode toutes les minutes
        public void processFeedbacksByPriority() {
            recruiterFeedbackService.processFeedbacksByPriority();
        }
    }


    @Override
    public List<Feedback> getReclamationsByMonth(int month) {

        return feedbackRepository.findByMonth(month);
    }


    // Méthode pour récupérer la liste des réclamations triées par date limite
    public List<Feedback> getReclamationsByDueDate() {
        return feedbackRepository.findAllByOrderByDateLimiteAsc();
    }


}












