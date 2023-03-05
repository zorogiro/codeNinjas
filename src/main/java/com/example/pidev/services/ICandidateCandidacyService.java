package com.example.pidev.services;

import com.example.pidev.entities.Appointment;
import com.example.pidev.entities.Candidacy;
import com.example.pidev.entities.StatCandidacy;
import com.example.pidev.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ICandidateCandidacyService {

    Candidacy addCandidacy(int idCandidate,int idOffer);
    public List<Appointment> getAllAppointmentByIdCandidate(int idCandidate);
    public List<Appointment> getAppointmentsWithCloseDateAndIdCandidate(int idCandidate);
    public Appointment getAppointmentByIdCandidacy(int idCandidacy);
    void deleteCandidacy(int idCandidacy);
    List<Candidacy> findCandidacyByidCandidate(int idCandidate);
     StatCandidacy statCandidacyByIdCandidate(int idCandidacy);
     List<Candidacy> findCandidacyForOfferExpired(int idCandidate);
}
