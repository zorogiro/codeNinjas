package com.example.pidev.services;

import com.example.pidev.entities.Appointment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IRecuiterAppointmentService {
    Appointment addAppoitment(Appointment appointment,int idCandidacy,int idRecuiter);
    boolean deleteAppointment(int idAppointment);
    Appointment updateAppointmentDate(int idAppointment, Date newDate);
    List<Appointment> getAppointmentsWithCloseDate(Date date);
}
