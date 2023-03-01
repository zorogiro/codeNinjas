package com.example.pidev.repository;

import com.example.pidev.entities.Housing;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Repository
public interface HousingRepository extends JpaRepository<Housing, Integer> {

    @Query("SELECT r FROM Housing r WHERE r.price= :prix")

    //@Query("SELECT count (cc.idFeedback) FROM Feedback cc WHERE cc.dateCreation =:dateCreation ")
    List<Housing> findByPrixLessThan(double prix);

}
