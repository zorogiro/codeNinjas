package com.example.pidev.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserKey implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;
    private int cin;
}
