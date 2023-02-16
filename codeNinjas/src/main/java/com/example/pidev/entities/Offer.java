package com.example.pidev.entities;

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
    private Integer idOffer;
    private String title;
    private String image;
    private String description;
    private double scoreOffer;
    private Date dateCreation;
    private int nbrPlaceDisponible;
    private Date dateExpiration;
    @ManyToOne
    private User recruiter;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "offer")
    private List<Candidacy> candidacies;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "offer")
    private List<Feedback> feedbacks;
    @ManyToOne
    University universities;
}
