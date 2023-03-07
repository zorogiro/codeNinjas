package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.AppointmentRepository;
import com.esprit.tn.forum.repository.CandidacyRepository;
import com.esprit.tn.forum.repository.OfferRepository;
import com.esprit.tn.forum.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CandidateCandidacyService implements ICandidateCandidacyService{
    @Autowired
    CandidacyRepository candidacyRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AuthService authService;
    @Autowired
    IEmailService iEmailService;
    @Override
    public Candidacy addCandidacy( int idOffer) {

        //User user=this.userRepository.findById(Long.valueOf(idCandidate)).get();
        User user=authService.getCurrentUser();
        System.out.println(user.getTypeUser());
        Offer offer=this.offerRepository.findById(Long.valueOf(idOffer)).get();
        System.out.println(offer.getScoreOffer());
        if(user.isCandidate())
        {
            Candidacy candidacy=new Candidacy();

            candidacy.setCandidate(user);
            System.err.println("****"+candidacy.getCandidate().getCin());
            candidacy.setTypeCandidacy(TypeCandidacy.OnHold);
            candidacy.setOffer(offer);

            Date date= java.sql.Date.valueOf(new LocalDate().toString());
            candidacy.setDateCreation(date);

            Candidacy candidacy1= this.candidacyRepository.save(candidacy);
            EmailDetails emailDetailsCandidate=new EmailDetails();
            emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
            emailDetailsCandidate.setSubject("Candidacy");
            emailDetailsCandidate.setMsgBody("Your Candidacy have been added .\n So your Candidacy is On Hold");
            iEmailService.sendSimpleMail(emailDetailsCandidate);
            /////
            EmailDetails emailDetailsRecuiter=new EmailDetails();
            emailDetailsRecuiter.setMsgBody("You have a new Candidacy for a Candidate name :\t "
                    +candidacy.getCandidate().getUsername()
                    +" \n "+
                    "http://localhost:8089/pidev/recuiterCandidacy/showCandidacyByidOffre/"+ offer.getIdOffer());
            System.err.println(offer.getRecruiter().getEmail());
            emailDetailsRecuiter.setRecipient(candidacy.getOffer().getRecruiter().getEmail());
            emailDetailsRecuiter.setSubject("New Candidacy");
            System.err.println(iEmailService.sendSimpleMail(emailDetailsRecuiter));
            return candidacy1;
        }
        return null;
    }

    @Override
    public void deleteCandidacy(int idCandidacy) {
        List<Appointment> appointments=appointmentRepository.findAll();
        Candidacy candidacy=candidacyRepository.findById(idCandidacy).get();

        for(Appointment appointment:appointments){
            if(appointment.getCandidacy().getIdCandidacy()==idCandidacy){
                appointmentRepository.delete(appointment);
                break;
            }
        }
        EmailDetails emailDetailsCandidate=new EmailDetails();
        emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
        emailDetailsCandidate.setSubject("Candidacy");
        emailDetailsCandidate.setMsgBody("Your Candidacy have been deleted .\n ");
        iEmailService.sendSimpleMail(emailDetailsCandidate);
        /////
        EmailDetails emailDetailsRecuiter=new EmailDetails();
        emailDetailsRecuiter.setMsgBody("You have a  Candidacy  deleted for a Candidate name :\t "
                +candidacy.getCandidate().getUsername()+" ");
        //System.err.println(candidacy.getOffer().getc);
        emailDetailsRecuiter.setRecipient(candidacy.getOffer().getRecruiter().getEmail());
        emailDetailsRecuiter.setSubject(" Candidacy deleted");
        System.err.println(iEmailService.sendSimpleMail(emailDetailsRecuiter));
        System.err.println(idCandidacy);
        this.candidacyRepository.deleteById(idCandidacy);
        System.err.println("Candidacy deleted");
    }

    @Override
    public List<Candidacy> findCandidacyByidCandidate() {
        List<Candidacy> candidacyList=this.candidacyRepository.findAll();
        List<Candidacy> candidacies=new ArrayList<Candidacy>();
       // User user= this.userRepository.findById(Long.valueOf(idCandidate)).get();
        User user=authService.getCurrentUser();
        System.err.println(user.getCin());
        for(Candidacy c : candidacyList){
            if(c.getCandidate().equals(user) && user.getTypeUser().equals(TypeUser.Candidate))            {
                candidacies.add(c);
            }
        }
        return candidacies;
    }

    @Override
    public StatCandidacy statCandidacyByIdCandidate() {
        User candidate=authService.getCurrentUser();
        //System.err.println(idCandidate);
        List<Candidacy> candidacies= candidacyRepository.findAll();
        //User candidate=userRepository.findById(Long.valueOf(idCandidate)).get();
        StatCandidacy statCandidacies=new StatCandidacy(candidate,0,0,0,0,0);
        statCandidacies.setCandidate(candidate);
        for(Candidacy c:candidacies){
            if(c.getCandidate().equals(candidate)){
                if (c.getTypeCandidacy().equals(TypeCandidacy.Accepted)) {
                    int nbr=statCandidacies.getNbrCandidacyAccepted();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyAccepted(nbr);
                }
                if (c.getTypeCandidacy().equals(TypeCandidacy.Refus)) {
                    int nbr=statCandidacies.getNbrCandidacyRefused();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyRefused(nbr);
                }
                if (c.getTypeCandidacy().equals(TypeCandidacy.Processing)) {
                    int nbr=statCandidacies.getNbrCandidacyProcessing();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyProcessing(nbr);
                }
                if (c.getTypeCandidacy().equals(TypeCandidacy.OnHold)) {
                    int nbr=statCandidacies.getNbrCandidacyOnHold();
                    nbr=nbr+1;
                    statCandidacies.setNbrCandidacyOnHold(nbr);
                }
                int nbr=statCandidacies.getNbrCandidacy();
                nbr=nbr+1;
                statCandidacies.setNbrCandidacy(nbr);
            }
        }
        return statCandidacies;
    }

    @Override
    public List<Candidacy> findCandidacyForOfferExpired() {

        //User candidate= userRepository.findById(Long.valueOf(idCandidate)).get();
        User candidate=authService.getCurrentUser();
        List<Candidacy> candidacies=candidacyRepository.findAll();
        Date date= java.sql.Date.valueOf(new LocalDate().toString());
        List<Candidacy> candidaciesFinal=new ArrayList<>();
        if(candidacies!=null)
        for(Candidacy c:candidacies){
            if(c.getCandidate().equals(candidate))
            {
                System.err.println((c.getOffer().getDateExpiration().getDayOfYear()-date.getDay()));
                if((c.getOffer().getDateExpiration().getDayOfYear()- date.getDate()<=5)){
                    candidaciesFinal.add(c);
                }

            }
        }
        return candidaciesFinal;
    }
    @Override

    public List<Appointment> getAllAppointmentByIdCandidate() {
       // User candidate= userRepository.findById(Long.valueOf(idCandidate)).get();
        User candidate=authService.getCurrentUser();
        List<Appointment> appointmentList=appointmentRepository.findAll();
        List<Appointment> appointments=new ArrayList<>();
        if(appointmentList!=null)
            for(Appointment appointment: appointmentList){
                if(appointment.getCandidacy().getCandidate().equals(candidate)){
                    appointments.add(appointment);
                }
            }
        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsWithCloseDateAndIdCandidate() {
        //User candidate= userRepository.findById(Long.valueOf(idCandidate)).get();
        User candidate=authService.getCurrentUser();
        List<Appointment> appointmentList=appointmentRepository.findAll();
        List<Appointment> appointments=new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        Date date1= new Date(timestamp.getTime());
        if(appointmentList!=null)
            for(Appointment appointment: appointmentList){
                if((appointment.getDateAppointment().getDay()+5>=date1.getDay())&&appointment.getCandidacy().getCandidate().equals(candidate)){
                    appointments.add(appointment);
                }
            }
        return appointments;
    }

    @Override
    public Appointment getAppointmentByIdCandidacy(int idCandidacy) {
        List<Appointment> appointments=appointmentRepository.findAll();
        Candidacy candidacy=candidacyRepository.findById(idCandidacy).get();
        for(Appointment appointment:appointments){
            if(appointment.getCandidacy().equals(candidacy))
                return appointment;
        }
        return null;
    }

}
