package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.Appointment;
import com.esprit.tn.forum.model.Candidacy;
import com.esprit.tn.forum.model.Offer;
import com.esprit.tn.forum.model.StatistiquesOffer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public interface IRecuiterCandidacyService {
    public Appointment addAppoitment(int idCandidacy);
    public Appointment updateAppointmentDate(int idAppointment, Date newDate);
    public List<Appointment> getAppointmentsWithCloseDate();
    public boolean deleteAppointment(int idAppointment);
    public Candidacy makeCandidacyOnProcessing(int candidacy);
    public Candidacy makeCandidacyAccepted(int candidacy);
    public Candidacy makeCandidacyRefused(int candidacy);
    public List<Candidacy> showCandidacyByidOffre(int idOffre);
    public Map<Offer, StatistiquesOffer> showNumberCandidacyByOffer();
}
