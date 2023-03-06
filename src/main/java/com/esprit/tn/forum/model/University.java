package com.esprit.tn.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "University")
public class University implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUniversity;
    private String name;
    private String Email;
    private String image;
    @OneToOne
    private Country country;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "recruterId", referencedColumnName = "userId")
    @JsonIgnore
    private User Recruiter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="university")
    @JsonIgnore
    private Set<Offer> Offers;
}
