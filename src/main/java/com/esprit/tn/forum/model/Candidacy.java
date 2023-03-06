package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

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
    private Long idCandidacy;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "candidateId", referencedColumnName = "userId")
    private User candidate;
    @ManyToOne
    private Offer offer;
    @OneToOne(mappedBy = "candidacy")
    private Appointment appointment;
    private LocalDateTime dateCreation;
    @Enumerated(EnumType.STRING)
    private Status status;
    private double scoreStudent;

}
