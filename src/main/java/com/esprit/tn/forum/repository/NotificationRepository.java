package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< Updated upstream
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByUserAndReadOrderByCreatedAtDesc(User recipient, boolean b);
=======

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserOrderByCreatedDateDesc(User recipient);

>>>>>>> Stashed changes
}
