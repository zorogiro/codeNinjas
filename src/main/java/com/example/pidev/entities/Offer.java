package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private int idOffer;
    private String title;
    private String description;
    private double scoreOffer;
    private Date dateCreation;
    private int nbrPlaceDisponible;
    private Date dateExpiration;
    @Enumerated(EnumType.STRING)
    private TypeOffer typeOffer;
    @ManyToOne
    private User recruiter;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "offer")
    @JsonIgnore
    private List<Candidacy> candidacies;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "offer")
    @JsonIgnore
    private List<Feedback> feedbacks;
}
