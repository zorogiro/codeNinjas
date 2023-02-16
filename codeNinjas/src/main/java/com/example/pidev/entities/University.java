package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "University")
public class University implements Serializable {
    @Id
    private Integer idUniversity;
    private String name;
   // @OneToOne
   // private Country country;
    @OneToOne
    private User recruiter;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="universities")
    @JsonIgnore
    private Set<Offer> Offers;
}
