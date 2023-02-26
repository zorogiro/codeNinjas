package com.example.pidev.services;

import com.example.pidev.entities.Appointment;
import com.example.pidev.entities.Candidacy;
import com.example.pidev.entities.TypeCandidacy;
import com.example.pidev.entities.User;
import com.example.pidev.repositories.AppointmentRepository;
import com.example.pidev.repositories.CandidacyRepository;
import com.example.pidev.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RecuiterAppointmentService implements IRecuiterAppointmentService{

    private AppointmentRepository appointmentRepository;
    private UserRepository userRepository;
    private CandidacyRepository candidacyRepository;
    @Override
    public Appointment addAppoitment(Appointment appointment,int idCandidacy,int idRecuiter) {
        Candidacy candidacy=candidacyRepository.findById(idCandidacy).get();
        User user=userRepository.findById(idRecuiter).get();
        if(candidacy.getTypeCandidacy().equals(TypeCandidacy.Accepted)){
            appointment.setCandidacy(candidacy);
            appointment.setRecruiter(user);
            return appointmentRepository.save(appointment);
        }

        return null;
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
    public List<Appointment> getAppointmentsWithCloseDate(Date date) {

        List<Appointment> appointments=appointmentRepository.findAll();
        List<Appointment>appointments1=new ArrayList<>();
       // Date date1=Date.parse( LocalDate.now().toString());
        for(Appointment appointment:appointments){
           // if(appointment.getDateAppointment().getDay()+5>)
        }
        return appointments1;
    }
}
