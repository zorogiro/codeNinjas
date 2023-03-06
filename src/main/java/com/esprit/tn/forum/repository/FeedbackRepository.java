package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // @Query("SELECT count (cc.idFeedback) FROM Feedback cc WHERE cc.dateCreation =:dateCreation ")
    // double calculerMoyenneReclamationsParUtilisateur();


    @Query("SELECT DATE(dateCreation) as date, COUNT(dateCreation) as count, AVG(rating) as average FROM Feedback GROUP BY DATE(dateCreation)")
    List<Object[]> findFeedbackStatsByDate();

    // @Query("SELECT count (cc.idFeedback) FROM Feedback cc WHERE cc.dateCreation =:dateCreation ")
    @Query("select f from Feedback f order by f.priority DESC")
    List<Feedback> findAllByOrderByPriorityDesc();

    List<Feedback> findAllByOrderByDateLimiteAsc();

    @Query("SELECT r FROM Feedback r WHERE MONTH(r.dateCreation) = :month")
    List<Feedback> findByMonth(@Param("month") int month);




}
