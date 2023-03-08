package com.esprit.tn.forum.dto;

import com.esprit.tn.forum.model.TypeCandidacy;
import com.esprit.tn.forum.model.TypeOffer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OfferDto {
    private Long idOffer;
    private String title;
    private String description;
    private double scoreOffer;
    private LocalDateTime dateCreation;
    private int nbrPlaceDisponible;
    private LocalDateTime dateExpiration;
    private TypeOffer typeOffer;
    private Long recruiterId;
    private Long universityId;
    private boolean applied;
    private TypeCandidacy typeCandidacy;

}
