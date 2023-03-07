package com.esprit.tn.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private User candidate;
    @ManyToOne
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private Offer offer;
    @OneToOne(mappedBy = "candidacy")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private Appointment appointment;
    private Date dateCreation;
    @Enumerated(EnumType.STRING)
    private TypeCandidacy typeCandidacy;
    @OneToMany(mappedBy = "candidacy")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Feedback> feedbacks;
}
