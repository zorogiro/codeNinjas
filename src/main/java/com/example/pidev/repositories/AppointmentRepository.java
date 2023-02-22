package com.example.pidev.repositories;

import com.example.pidev.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository  extends JpaRepository<Appointment,Integer> {
}
