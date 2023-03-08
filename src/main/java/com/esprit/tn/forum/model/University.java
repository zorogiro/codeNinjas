package com.esprit.tn.forum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @OneToOne(cascade = CascadeType.ALL)
    private Country country;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruterId", referencedColumnName = "userId")
    @JsonIgnore
    private User Recruiter;

}
