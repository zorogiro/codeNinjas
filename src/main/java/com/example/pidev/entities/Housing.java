package com.example.pidev.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Housing")
@Entity
public class Housing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHousing;
    private String address;
    @Enumerated(EnumType.STRING)
    private TypeHousing typeHousing;
    private double price;
    //@OneToOne
   // private University university;
}
