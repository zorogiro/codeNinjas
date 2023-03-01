package com.example.pidev.entities;

import com.example.pidev.service.OfferService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CsvRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCsvRow")
    private String idCsvRow;
    private double moy3;
    private double moy4;
    private double fr;
    private double ang;
    private double python;
    private double math;
    private double gl;
    private double spring;
    private double adf;
    private int marks;


    // Getters and setters
}