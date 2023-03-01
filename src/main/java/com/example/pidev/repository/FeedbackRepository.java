package com.example.pidev.repository;

import com.example.pidev.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {

   // @Query("SELECT count (cc.idFeedback) FROM Feedback cc WHERE cc.dateCreation =:dateCreation ")
    // double calculerMoyenneReclamationsParUtilisateur();


    @Query("SELECT DATE(dateCreation) as date, COUNT(dateCreation) as count, AVG(rating) as average FROM Feedback GROUP BY DATE(dateCreation)")
    List<Object[]> findFeedbackStatsByDate();


    List<Feedback> findAllByOrderByPriorityDesc();

    @Query("SELECT r FROM Feedback r WHERE MONTH(r.dateCreation) = :month")
    List<Feedback> findByMonth(@Param("month") int month);











}
