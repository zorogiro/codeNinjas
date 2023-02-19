package com.example.pidev.services;

import com.example.pidev.entities.*;
import com.example.pidev.repository.CondidacyRepository;
import com.example.pidev.repository.FeedbackRepository;
import com.example.pidev.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class recruiterFeedbackService implements IrecruiterFeedbackService {

FeedbackRepository feedbackRepository;
CondidacyRepository condidacyRepository;
UserRepository userRepository;


    @Override
    public List<Feedback> retrieveAllFeedback() {

       return  (List<Feedback>) feedbackRepository.findAll();

    }

    @Override
    public Feedback addFeedback(Feedback f ,int iduser,int cin,int idcondidacy) {
        UserKey id= new UserKey();
        id.setIdUser(iduser);
        id.setCin(cin);
        User user=this.userRepository.getReferenceById(id);
        f.setUser(user);
        Candidacy candidacy=this.condidacyRepository.getReferenceById(idcondidacy);
        f.setCandidacy(candidacy);
        return this.feedbackRepository.save(f);
    }

    @Override
    public Feedback updateFeedback(Feedback f) {
        return this.feedbackRepository.save(f);
    }

    @Override
    public Feedback removeFeedback(Integer idFeedback) {
        feedbackRepository.deleteById(idFeedback);
        return null;
    }
}
