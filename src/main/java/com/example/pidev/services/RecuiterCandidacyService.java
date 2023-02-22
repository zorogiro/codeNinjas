package com.example.pidev.services;
import com.example.pidev.entities.*;
import com.example.pidev.repositories.AppointmentRepository;
import com.example.pidev.repositories.CandidacyRepository;
import com.example.pidev.repositories.OfferRepository;
import com.twilio.twiml.voice.Client;
import lombok.AllArgsConstructor;
//import javax.mail.Session;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;


//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class RecuiterCandidacyService  implements IRecuiterCandidacyService{
    CandidacyRepository candidacyRepository;
    OfferRepository offerRepository;
    AppointmentRepository appointmentRepository;
    @Override
    public Candidacy makeCandidacyOnProcessing(int idcandidacy) {
        Candidacy candidacy=this.candidacyRepository.findById(idcandidacy).get();
        if(candidacy != null) {
            candidacy.setTypeCandidacy(TypeCandidacy.Processing);
            return this.candidacyRepository.save(candidacy);
        }
        return null;
    }

    @Override
    public Candidacy makeCandidacyAccepted(int idcandidacy) {
        Candidacy candidacy=this.candidacyRepository.getReferenceById(idcandidacy);
        if(candidacy!=null)
        {
            Offer offer=offerRepository.findById(candidacy.getOffer().getIdOffer()).get();
            int nbr=offer.getNbrPlaceDisponible();
            nbr=nbr-1;
            offer.setNbrPlaceDisponible(nbr);
            Offer offer1=offerRepository.save(offer);
            Appointment appointment=new Appointment();

            candidacy.setTypeCandidacy(TypeCandidacy.Accepted);
            /*try {
                System.out.println("////////////////////////");
                this.sendEmail("houssem.hassani@esprit.tn","hahah");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }*/
            Candidacy candidacy1= this.candidacyRepository.save(candidacy);
            Date date=new LocalDate().toDate();
            List<Appointment> appointments=appointmentRepository.findAll();
            boolean exist=false;
            for(Appointment appointment1:appointments){
                if(appointment1.getCandidacy().getIdCandidacy()==candidacy1.getIdCandidacy()){
                    exist=true;
                    break;
                }
            }
            if(!exist){
                appointment.setCandidacy(candidacy1);
                appointment.setDateAppointment(date);
                appointment.setRecruiter(offer1.getRecruiter());

                appointment.setLinkMeet(null);
                appointmentRepository.save(appointment);
            }
            //candidacy1.setAppointment(appointment1);
            return candidacy1;
        }



        return  null;
    }

    /*public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("houssemhassanii@gmail.com");
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    public void sendHtmlEmail() throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress("houssemhassanii@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, "houssem.hassani@esprit.tn");
        message.setSubject("Test email from Spring");

        String htmlContent = "<h1>This is a test Spring Boot email</h1>" +
                "<p>It can contain <strong>HTML</strong> content.</p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");
        System.err.println("4564654564");
        mailSender.send(message);
    }
    public static void sendEmail(String recepient,String s) throws Exception
    {
        System.err.println("Prepare");
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myAccountEmail="e.citoyen.tunisie@gmail.com";
        String password="E-citoyen2022";
        javax.mail.Session session=javax.mail.Session.getInstance(properties,new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(myAccountEmail,password);

            }
        });
        Message message = prepareMessage(session,myAccountEmail,recepient,s);
        Transport.send(message);
        System.err.println("envoyee avec succes");




    }



    private static Message prepareMessage(Session session,String myAccountEmail,String recepient,String msg) {
        try {
            Message message=new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress (recepient));
            message.setSubject("Attente de Confirmation d'inscription");
            message.setText(msg);
            message.reply(false);
            return message;

        }
        catch(Exception e)
        {
            e.getMessage();
        }
        return null;
    }*/

    @Override
    public Candidacy makeCandidacyRefused(int idcandidacy) {
        Candidacy candidacy=this.candidacyRepository.findById(idcandidacy).get();
        if(candidacy!=null)
        {
            candidacy.setTypeCandidacy(TypeCandidacy.Refus);
            return  this.candidacyRepository.save(candidacy);
        }
        return null;
    }

    @Override
    public List<Candidacy> showCandidacyByidOffre(int idOffre) {
        Offer offer=this.offerRepository.findById(idOffre).get();
        List<Candidacy> candidacies= offer.getCandidacies();
        List<Candidacy> candidacyList=new ArrayList<>();
        for(Candidacy c:candidacies)
        {
            if(c.getOffer().equals(offer))
                candidacyList.add(c);
        }
        return candidacyList;
    }

    @Override
    public Map<Offer,StatistiquesOffer> showNumberCandidacyByOffer() {

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
}
