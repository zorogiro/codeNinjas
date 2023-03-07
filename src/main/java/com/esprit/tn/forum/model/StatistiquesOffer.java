package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatistiquesOffer {
    private int numCandidacy;
    private int numCandidacyRefused;
    private int numCandidacyAccepted;
    private int numCandidacyProcessing;
    private int numCandidacyOnHold;

}
