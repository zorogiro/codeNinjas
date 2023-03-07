package com.esprit.tn.forum.service;

import com.esprit.tn.forum.model.*;
import com.esprit.tn.forum.repository.AppointmentRepository;
import com.esprit.tn.forum.repository.CandidacyRepository;
import com.esprit.tn.forum.repository.OfferRepository;
import com.esprit.tn.forum.repository.UserRepository;
import com.twilio.twiml.voice.Client;
import lombok.AllArgsConstructor;
//import javax.mail.Session;

import org.joda.time.LocalDate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class RecuiterCandidacyService  implements IRecuiterCandidacyService{
    CandidacyRepository candidacyRepository;
    IEmailService iEmailService;
    UserRepository userRepository;
    public AuthService authService;
    OfferRepository offerRepository;
    AppointmentRepository appointmentRepository;
    @Override
    public Candidacy makeCandidacyOnProcessing(int idcandidacy) {
        Candidacy candidacy=this.candidacyRepository.getById(idcandidacy);
        if(candidacy != null) {
            candidacy.setTypeCandidacy(TypeCandidacy.Processing);
            EmailDetails emailDetailsCandidate=new EmailDetails();
            emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
            emailDetailsCandidate.setSubject("Candidacy");
            emailDetailsCandidate.setMsgBody("Your Candidacy wich have date"+ candidacy.getDateCreation()+" have been changend for "+TypeCandidacy.Processing+
                    "by the recruiter "+candidacy.getOffer().getRecruiter().getUsername()+
                    " his mail : "+
                    candidacy.getOffer().getRecruiter().getEmail());
            iEmailService.sendSimpleMail(emailDetailsCandidate);
            return this.candidacyRepository.save(candidacy);
        }
        return null;
    }

    @Override
    public Candidacy makeCandidacyAccepted(int idcandidacy) {
        Candidacy candidacy=this.candidacyRepository.getById(idcandidacy);
        if(candidacy!=null)
        {
            Offer offer=offerRepository.findById(candidacy.getOffer().getIdOffer()).get();
            int nbr=offer.getNbrPlaceDisponible();
            nbr--;
            offer.setNbrPlaceDisponible(nbr);
            Offer offer1=offerRepository.save(offer);
            EmailDetails emailDetailsCandidate=new EmailDetails();
            emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
            emailDetailsCandidate.setSubject("Candidacy");
            emailDetailsCandidate.setMsgBody("Your Candidacy wich have date"+ candidacy.getDateCreation()+" have been acceted by"
                    +candidacy.getOffer().getRecruiter().getUsername()+
                    " his mail : "+
                    candidacy.getOffer().getRecruiter().getEmail());
            iEmailService.sendSimpleMail(emailDetailsCandidate);
            EmailDetails emailDetailsRecuiter=new EmailDetails();
            emailDetailsRecuiter.setRecipient(candidacy.getOffer().getRecruiter().getEmail());
            emailDetailsRecuiter.setSubject("Candidacy");
            emailDetailsRecuiter.setMsgBody("You accepted Candidacy for "+
                    candidacy.getCandidate().getUsername()+
                    " .\n So your offer have   "+offer1.getNbrPlaceDisponible()+"place disponible"+
                    "you can send mail for your new candidate via : "+candidacy.getCandidate().getEmail());

            iEmailService.sendSimpleMail(emailDetailsRecuiter);
            candidacy.setTypeCandidacy(TypeCandidacy.Accepted);
             return this.candidacyRepository.save(candidacy);
        }



        return  null;
    }


    @Override
    public Candidacy makeCandidacyRefused(int idcandidacy) {
        Candidacy candidacy=this.candidacyRepository.findById(idcandidacy).get();
        if(candidacy!=null)
        {
            candidacy.setTypeCandidacy(TypeCandidacy.Refus);
            EmailDetails emailDetailsCandidate=new EmailDetails();
            emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
            emailDetailsCandidate.setSubject("Candidacy");
            emailDetailsCandidate.setMsgBody("Your Candidacy wich have date :"+ candidacy.getDateCreation()+" have been refused " +
                    " . by the recruiter :  "+candidacy.getOffer().getRecruiter().getUsername());
            iEmailService.sendSimpleMail(emailDetailsCandidate);
            return  this.candidacyRepository.save(candidacy);
        }
        return null;
    }

    @Override
    public List<Candidacy> showCandidacyByidOffre(int idOffre) {
        Offer offer=this.offerRepository.findById(Long.valueOf(idOffre)).get();
         List<Candidacy> candidacies= candidacyRepository.getCandidaciesByOffer(offer);
         return candidacies;

    }

    @Override
    public Map<Offer, StatistiquesOffer> showNumberCandidacyByOffer() {

        Map<Offer,StatistiquesOffer> statistiquesOffers =new HashMap<>();
        List<Candidacy> candidacies=candidacyRepository.findAll();
        for(Candidacy c : candidacies){
            statistiquesOffers.put(c.getOffer(),new StatistiquesOffer());
        }
        for (Map.Entry mapentry : statistiquesOffers.entrySet())
        {
            Offer offer= (Offer) mapentry.getKey();
            StatistiquesOffer statistiquesOffer=(StatistiquesOffer) mapentry.getValue();
            System.err.println((offer.getScoreOffer()+" ** value : "+mapentry.getValue().toString()));
            for(Candidacy c : candidacies){
                if(c.getOffer().equals(offer))
                {
                    if(c.getTypeCandidacy().equals(TypeCandidacy.Accepted))
                    {
                        int nbr = statistiquesOffer.getNumCandidacyAccepted();
                        nbr=nbr+1;
                        statistiquesOffer.setNumCandidacyAccepted(nbr);
                    }
                    if(c.getTypeCandidacy().equals(TypeCandidacy.Refus))
                    {
                        int nbr = statistiquesOffer.getNumCandidacyRefused();
                        nbr=nbr+1;
                        statistiquesOffer.setNumCandidacyRefused(nbr);
                    }
                    if(c.getTypeCandidacy().equals(TypeCandidacy.OnHold))
                    {
                        int nbr = statistiquesOffer.getNumCandidacyOnHold();
                        nbr=nbr+1;
                        statistiquesOffer.setNumCandidacyOnHold(nbr);
                    }
                    if(c.getTypeCandidacy().equals(TypeCandidacy.Processing))
                    {
                        int nbr = statistiquesOffer.getNumCandidacyProcessing();
                        nbr=nbr+1;
                        statistiquesOffer.setNumCandidacyProcessing(nbr);
                    }
                    int nbr = statistiquesOffer.getNumCandidacy();
                    nbr=nbr+1;
                    statistiquesOffer.setNumCandidacy(nbr);
                    mapentry.setValue(statistiquesOffer);
                }
            }
        }
            return statistiquesOffers;
    }
    @Scheduled(cron = "0 0 16 1 * ?")
    public void deleteCandidacyHavingOfferDateExpirationOver(){
        List<Candidacy> candidacies=candidacyRepository.findAll();
       // Date date= java.sql.Date.valueOf(new LocalDate().toString());
        for(Candidacy candidacy:candidacies){
            if((candidacy.getOffer().getDateExpiration().isBefore(LocalDateTime.now()))&& !candidacy.getTypeCandidacy().equals(TypeCandidacy.Accepted)){
                candidacyRepository.delete(candidacy);
                System.err.println("candidacy over deleted");
                EmailDetails emailDetailsCandidate=new EmailDetails();
                emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
                emailDetailsCandidate.setSubject("Candidacy");
                emailDetailsCandidate.setMsgBody("Your Candidacy "+ candidacy.getTypeCandidacy()+" have been deleted because the offer is expired" +
                        " .\n So you can send mail for the recruiter :  "+
                        " "+candidacy.getOffer().getRecruiter().getUsername() +" his mail : "+
                        candidacy.getOffer().getRecruiter().getEmail());
                iEmailService.sendSimpleMail(emailDetailsCandidate);
            }
        }
    }
    @Scheduled(cron = "0 0 18 2 * ?")
    public void deleteCandidacyHavingOfferDateExpirationOverAndIsAcceptedOrProcessing(){
        List<Candidacy> candidacies=candidacyRepository.findAll();
        Date date= java.sql.Date.valueOf(new LocalDate().toString());
        System.err.println(date.toString());
        System.err.println(date.getDay()+"*******");

        for(Candidacy candidacy:candidacies){
            System.err.println(candidacy.getOffer().getDateExpiration().getDayOfYear());
            if((candidacy.getOffer().getDateExpiration().isAfter(LocalDateTime.now()))&& (candidacy.getTypeCandidacy().equals(TypeCandidacy.Accepted) ||
                    candidacy.getTypeCandidacy().equals(TypeCandidacy.Processing))){
                candidacyRepository.delete(candidacy);
                EmailDetails emailDetailsCandidate=new EmailDetails();
                emailDetailsCandidate.setRecipient(candidacy.getCandidate().getEmail());
                emailDetailsCandidate.setSubject("Candidacy");
                emailDetailsCandidate.setMsgBody("Your Candidacy "+ candidacy.getTypeCandidacy()+" have been deleted because is expired" +
                        " .\n So you can send mail for the recruiter :  "+
                        " "+candidacy.getOffer().getRecruiter().getUsername() +" his mail : "+
                        candidacy.getOffer().getRecruiter().getEmail());
                iEmailService.sendSimpleMail(emailDetailsCandidate);
                System.err.println("candidacy for candidate CIN : "+candidacy.getCandidate().getCin()+" \n have a type :"+candidacy.getTypeCandidacy()+
                        " \n and over date is deleted ");
            }
        }
    }
    @Override
    public Appointment addAppoitment(int idCandidacy) {
        User user=authService.getCurrentUser();
        System.err.println("****"+idCandidacy);
        Candidacy candidacy=new Candidacy();
        if(candidacyRepository.findById(idCandidacy).get()!=null)
        {
            candidacy=candidacyRepository.getById(idCandidacy);
        }

        Appointment appointment=new Appointment();

        if(candidacy.getTypeCandidacy().equals(TypeCandidacy.Processing)){
            appointment.setCandidacy(candidacy);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneDayLater = now.plusDays(7);
            Timestamp timestamp = Timestamp.valueOf(oneDayLater);
            Date  date1= new Date(timestamp.getTime());
            appointment.setDateAppointment(date1);
            appointment.setRecruiter(user);
            appointment.setLinkMeet("https://meet.google.com/pzk-sxkb-vgt?fbclid=IwAR1qg4ZLwkfjEMJz2cctgi5zRkLxfOtd7nz72fuCBuEdp-9YkBusfTzHMRw");
            return appointmentRepository.save(appointment);
        }

        return null;
    }
    @Override
    public Appointment updateAppointmentDate(int idAppointment, Date newDate) {

        Appointment appointment=appointmentRepository.findById(idAppointment).get();
        if (appointment!=null){
            appointment.setDateAppointment(newDate);
            return appointmentRepository.save(appointment);
        }

        return null;
    }
    @Override
    public List<Appointment> getAppointmentsWithCloseDate()
    {
        User user=authService.getCurrentUser();

        System.err.println(user.getEmail());
        List<Appointment> appointments=appointmentRepository.findAll();
        List<Appointment>appointments1=new ArrayList<>();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        Date  date1= new Date(timestamp.getTime());
        //System.out.println(dtf.format(now));
        for(Appointment appointment:appointments){
            if((appointment.getDateAppointment().getDay()+5>=date1.getDay())&&
                    (appointment.getRecruiter().equals(user))){
                appointments1.add(appointment);
            }
        }
        return appointments1;
    }
    @Override
    public boolean deleteAppointment(int idAppointment) {

        Appointment appointment=appointmentRepository.findById(idAppointment).get();
        if(appointment!=null){
            appointmentRepository.delete(appointment);
            return true;
        }
        return false;
    }

}
