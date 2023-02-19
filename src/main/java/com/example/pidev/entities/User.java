package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

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
   @EmbeddedId
    private UserKey idUser;
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
    private University university;
    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private List<Candidacy> candidacies;
    @OneToMany(mappedBy = "recruiter")
    @JsonIgnore
    private List<Offer> offres;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Feedback> feedbacks;

}
