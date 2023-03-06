package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class CsvRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCsvRow")
    private double userId;
    private double moy3;
    private double moy4;
    private double fr;
    private double ang;
    private double python;
    private double math;
    private double gl;
    private double spring;
    private double adf;
}
