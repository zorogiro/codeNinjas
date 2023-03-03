package com.example.pidev.services;

import com.example.pidev.entities.Feedback;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
public interface IrecruiterFeedbackService {

    List<Feedback> retrieveAllFeedback();

    Feedback addFeedback (Feedback f ,int iduser,int cin,int idcondidacy);

    Feedback updateFeedbackrecruiter (Feedback f);

    Feedback updateFeedbackrecruiterr (Feedback f);

    Feedback removeFeedback(Integer  idFeedback);


    public List<Feedback> getReclamationsByMonth(int month);


  //  public List<Feedback> getReclamationsByYear(int year);



    public List<Feedback> getReclamationsByDueDate();



















}
