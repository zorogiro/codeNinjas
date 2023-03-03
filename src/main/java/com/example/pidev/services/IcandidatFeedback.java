package com.example.pidev.services;

import com.example.pidev.entities.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IcandidatFeedback {
    List<Feedback> retrieveAllFeedback();

    Feedback addcandidatFeedback (Feedback f ,int idoffer,int idu,int cin);

    Feedback updatecandidatFeedback (Feedback f);
    Feedback updatecandidatFeedbackk (Feedback f);

    void removecandidatFeedback(int  idFeedback);

    double calculerMoyenneReclamationsParUtilisateur();

    public List<Feedback> getReclamationsByDueDate();


}
