package com.esprit.tn.forum.controller;


import com.esprit.tn.forum.model.Notification;
import com.esprit.tn.forum.service.AuthService;
import com.esprit.tn.forum.service.NotificationService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthService authService;

    @GetMapping("/unread/{id}")
    public ResponseEntity<List<Notification>> getUnreadNotifications() {
        List<Notification> unreadNotifications = notificationService.getUnreadNotifications(authService.getCurrentUser());
        return ResponseEntity.ok(unreadNotifications);
    }

    @PostMapping("/mark-as-read")
    public ResponseEntity<Void> markAsRead(@RequestBody List<Long> notificationIds) {
        notificationService.markAsRead(notificationIds);
        return ResponseEntity.ok().build();
    }
}
