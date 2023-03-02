package com.esprit.tn.forum.service;


import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Transactional
public class NotificationService {

    private NotificationRepository notificationRepository;



    public List<Notification> getUnreadNotifications(User recipient) {
        List<Notification> unreadNotifications = notificationRepository.findByUserOrderByCreatedDateDesc(recipient);
        return unreadNotifications.stream()
                .filter(notification -> !notification.isVu())
                .collect(Collectors.toList());
    }

    public void sendNotification(User recipient, String message) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setMessage(message);
        notification.setVu(false);
        notificationRepository.save(notification);
    }


    @Transactional
    public void markAsRead(List<Long> notificationIds) {
        List<Notification> notifications = notificationRepository.findAllById(notificationIds);
        notifications.forEach(notification -> notification.setVu(true));
        notificationRepository.saveAll(notifications);
    }
}
