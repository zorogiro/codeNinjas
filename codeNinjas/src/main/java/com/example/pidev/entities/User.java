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
@Table(name = "User")
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idUser;
    private String firstName;
    private String lastName;
    private String userName;
    private Gender sexe;
    @Id
    private int cin;
    private String email;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    private String password;
    private Date dateCreation;
    @OneToOne(mappedBy = "recruiter")
    private University university;
    @OneToMany(mappedBy = "candidate")
    private List<Candidacy> candidacies;
    @OneToMany(mappedBy = "recruiter")
    private List<Offer> offres;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Feedback> feedbacks;

}
