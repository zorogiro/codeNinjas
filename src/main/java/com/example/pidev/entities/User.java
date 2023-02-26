package com.example.pidev.entities;

import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.core.mapping.Document;


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
    private String cin;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    private String password;
    private Date dateCreation;
    @OneToOne(mappedBy = "recruiter")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private University university;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Candidacy> candidacies;
    @OneToMany(mappedBy = "recruiter")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Offer> offres;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Feedback> feedbacks;

}
