package com.example.pidev.services;

import com.example.pidev.entities.Appointment;
import com.example.pidev.entities.Candidacy;
import com.example.pidev.entities.User;
import com.example.pidev.repositories.AppointmentRepository;
import com.example.pidev.repositories.CandidacyRepository;
import com.example.pidev.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAppointmentService implements ICandidateAppointmentService{
    CandidacyRepository candidacyRepository;
    AppointmentRepository appointmentRepository;
    UserRepository userRepository;
    @Override
    public List<Appointment> getAllAppointmentByIdCandidate(int idCandidate) {
        User candidate= userRepository.findById(idCandidate).get();
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
    public List<Appointment> getAppointmentsWithCloseDateAndIdCandidate(int idCandidate) {
        User candidate= userRepository.findById(idCandidate).get();
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
