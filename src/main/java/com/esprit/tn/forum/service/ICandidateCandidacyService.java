package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.Appointment;
import com.esprit.tn.forum.model.Candidacy;
import com.esprit.tn.forum.model.StatCandidacy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ICandidateCandidacyService {

    Candidacy addCandidacy( int idOffer);
    public List<Appointment> getAllAppointmentByIdCandidate();
    public List<Appointment> getAppointmentsWithCloseDateAndIdCandidate();
    public Appointment getAppointmentByIdCandidacy(int idCandidacy);
    void deleteCandidacy(int idCandidacy);
    List<Candidacy> findCandidacyByidCandidate();
     StatCandidacy statCandidacyByIdCandidate();
     List<Candidacy> findCandidacyForOfferExpired();
}
