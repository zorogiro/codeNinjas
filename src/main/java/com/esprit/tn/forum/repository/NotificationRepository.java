package com.esprit.tn.forum.repository;

import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserOrderByCreatedDateDesc(User recipient);

}
