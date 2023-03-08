package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IrecruiterFeedbackService {

    List<Feedback> retrieveAllFeedback();

    Feedback addFeedback(Feedback f, int idcondidacy);

    Feedback updateFeedbackrecruiter(Feedback f);

    Feedback updateFeedbackrecruiterr(Feedback f);

    Feedback removeFeedback(Long idFeedback);


    List<Feedback> getReclamationsByMonth(int month);


    List<Feedback> getReclamationsByDueDate();


}
