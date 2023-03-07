package com.esprit.tn.forum.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "Score")
public class Score {


    private String cinUser;
    private List<Matiere> matieres;
    private float moyenneGenerale;
    private float scoreTotale;
    private boolean isAccepted;
    private File file=null;
    public Score() {}




}