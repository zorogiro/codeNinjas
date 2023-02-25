package com.example.pidev.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "score")
public class Score {

    private User user;
    private List<Matiere> matieres;
    private float moyenneGenerale;
    public Score() {}

    public Score(User user) {
        this.user = user;
        this.matieres=new ArrayList<>();
        this.moyenneGenerale=0;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, name='%f']",
                user.getLastName(),this.getMoyenneGenerale() );
    }
}
