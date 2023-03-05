package com.example.pidev.controllers;

import com.example.pidev.entities.Appointment;
import com.example.pidev.services.CandidateAppointmentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;

@RestController("appointmentcandidate")
@AllArgsConstructor
@NoArgsConstructor
public class CandidateAppointmentController {
    CandidateAppointmentService candidateAppointmentService;






}
