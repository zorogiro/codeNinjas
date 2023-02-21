package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


    List<Notification> findByUserAndReadOrderByCreatedAtDesc(User recipient, boolean b);
}
