package com.example.pidev.services;

import com.example.pidev.entities.Feedback;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IrecruiterFeedbackService {

    List<Feedback> retrieveAllFeedback();

    Feedback addFeedback (Feedback f ,int iduser,int cin,int idcondidacy);

    Feedback updateFeedback (Feedback f);

    Feedback removeFeedback(Integer  idFeedback);
}
