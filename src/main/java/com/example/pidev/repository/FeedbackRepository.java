package com.example.pidev.repository;

import com.example.pidev.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {

   // @Query("SELECT count (cc.idFeedback) FROM Feedback cc WHERE cc.dateCreation =:dateCreation ")
    // double calculerMoyenneReclamationsParUtilisateur();

}
