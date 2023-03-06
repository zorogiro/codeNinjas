package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IcandidatFeedback {
    List<Feedback> retrieveAllFeedback();

    Feedback addcandidatFeedback (Feedback f ,Long idoffer);

    Feedback updatecandidatFeedback (Feedback f);
    Feedback updatecandidatFeedbackk (Feedback f);

    void removecandidatFeedback(Long  idFeedback);

    double calculerMoyenneReclamationsParUtilisateur();

    public List<Feedback> getReclamationsByDueDate();


}
