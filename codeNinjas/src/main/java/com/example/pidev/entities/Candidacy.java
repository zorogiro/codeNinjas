package com.example.pidev.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Candidacy")
public class Candidacy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCandidacy")
    private int idCandidacy;
    @ManyToOne
    private User candidate;
    @ManyToOne
    private Offer offer;
    @OneToOne(mappedBy = "candidacy")
    private Appointment appointment;
    private Date dateCreation;
    @Enumerated(EnumType.STRING)
    private TypeCandidacy typeCandidacy;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "candidacy")
    private List<Feedback> feedbacks;
}
