package com.example.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Feedback")
@Proxy(lazy = false)

public class Feedback  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFeedback;
    private String subject;
    private String content;
    @ManyToOne
    private Offer offer;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    private Candidacy candidacy;
    private Date dateCreation;
    private int rating;
    private String priority;

}
