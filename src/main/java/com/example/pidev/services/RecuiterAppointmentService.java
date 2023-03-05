package com.example.pidev.services;

import com.example.pidev.entities.Appointment;
import com.example.pidev.entities.Candidacy;
import com.example.pidev.entities.TypeCandidacy;
import com.example.pidev.entities.User;
import com.example.pidev.repositories.AppointmentRepository;
import com.example.pidev.repositories.CandidacyRepository;
import com.example.pidev.repositories.UserRepository;
import com.example.pidev.repositories.UserRepositoryy;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RecuiterAppointmentService implements IRecuiterAppointmentService{

     AppointmentRepository appointmentRepository;
     UserRepositoryy userRepository;
     CandidacyRepository candidacyRepository;







}
