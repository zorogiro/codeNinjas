package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
<<<<<<< Updated upstream
import java.time.LocalDateTime;
=======
import java.time.Instant;
>>>>>>> Stashed changes


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
<<<<<<< Updated upstream
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean read;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
=======
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private boolean vu;

    @Column(name = "created_date")
    private Instant createdDate;
>>>>>>> Stashed changes

}
