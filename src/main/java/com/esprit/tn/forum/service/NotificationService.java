package com.esprit.tn.forum.service;

import com.esprit.tn.forum.dto.NotificationDto;
import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data

@AllArgsConstructor
@Slf4j
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

//    private final NotificationMapper notificationMapper;


    public List<Notification> getUnreadNotifications(User recipient) {
        List<Notification> unreadNotifications = notificationRepository.findByUserAndReadOrderByCreatedAtDesc(recipient, false);
        return unreadNotifications;
    }

    public void sendNotification(User recipient, String message) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setMessage(message);
        notification.setRead(false);
        notificationRepository.save(notification);
    }


    @Transactional
    public void markAsRead(List<Long> notificationIds) {
        List<Notification> notifications = notificationRepository.findAllById(notificationIds);
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);
    }
}
