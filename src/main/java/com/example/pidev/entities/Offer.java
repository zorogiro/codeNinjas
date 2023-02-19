package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

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
@Proxy(lazy = false)
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
    @ManyToOne
    private User recruiter;
    @OneToMany(mappedBy = "offer")
    @JsonIgnore
    private List<Candidacy> candidacies;
    @OneToMany(mappedBy = "offer")
    @JsonIgnore
    private List<Feedback> feedbacks;
}
