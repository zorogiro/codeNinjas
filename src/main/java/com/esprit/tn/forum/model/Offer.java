package com.esprit.tn.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Offer")
public class Offer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idOffer")
    private Long idOffer;
    private String title;
    private String description;
    private double scoreOffer;
    private LocalDateTime dateCreation;
    private int nbrPlaceDisponible;
    private LocalDateTime dateExpiration;
    @Enumerated(EnumType.STRING)
    private TypeOffer typeOffer;
    private boolean applied;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recruiterId", referencedColumnName = "userId")
    private User recruiter;
    @ManyToOne
    University university;




}
