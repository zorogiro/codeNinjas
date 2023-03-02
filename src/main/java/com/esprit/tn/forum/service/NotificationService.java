package com.esprit.tn.forum.service;

<<<<<<< Updated upstream
import com.esprit.tn.forum.dto.NotificationDto;
=======

>>>>>>> Stashed changes
import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.model.User;
import com.esprit.tn.forum.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< Updated upstream
=======
import lombok.NoArgsConstructor;
>>>>>>> Stashed changes
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
<<<<<<< Updated upstream

@Service
@Data

@AllArgsConstructor
=======
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
>>>>>>> Stashed changes
@Slf4j
@Transactional
public class NotificationService {

<<<<<<< Updated upstream
    private final NotificationRepository notificationRepository;

//    private final NotificationMapper notificationMapper;


    public List<Notification> getUnreadNotifications(User recipient) {
        List<Notification> unreadNotifications = notificationRepository.findByUserAndReadOrderByCreatedAtDesc(recipient, false);
        return unreadNotifications;
=======
    private NotificationRepository notificationRepository;



    public List<Notification> getUnreadNotifications(User recipient) {
        List<Notification> unreadNotifications = notificationRepository.findByUserOrderByCreatedDateDesc(recipient);
        return unreadNotifications.stream()
                .filter(notification -> !notification.isVu())
                .collect(Collectors.toList());
>>>>>>> Stashed changes
    }

    public void sendNotification(User recipient, String message) {
        Notification notification = new Notification();
        notification.setUser(recipient);
        notification.setMessage(message);
<<<<<<< Updated upstream
        notification.setRead(false);
=======
        notification.setVu(false);
>>>>>>> Stashed changes
        notificationRepository.save(notification);
    }


    @Transactional
    public void markAsRead(List<Long> notificationIds) {
        List<Notification> notifications = notificationRepository.findAllById(notificationIds);
<<<<<<< Updated upstream
        notifications.forEach(notification -> notification.setRead(true));
=======
        notifications.forEach(notification -> notification.setVu(true));
>>>>>>> Stashed changes
        notificationRepository.saveAll(notifications);
    }
}
