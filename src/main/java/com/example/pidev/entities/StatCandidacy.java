package com.example.pidev.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class StatCandidacy {
    private User candidate;
    private int nbrCandidacy;
    private int nbrCandidacyAccepted;
    private int nbrCandidacyRefused;
    private int nbrCandidacyProcessing;
    private int nbrCandidacyOnHold;
}
