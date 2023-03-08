package com.esprit.tn.forum.model;

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
import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Feedback")
@Proxy(lazy = false)

public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFeedback;
    private String subject;
    private String content;
    @ManyToOne
    @JsonIgnore
    private Offer offer;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JsonIgnore
    private Candidacy candidacy;
    private LocalDateTime dateCreation;
    private int rating;
    private String priority;
    private String status;
    private LocalDateTime dateLimite;


}
