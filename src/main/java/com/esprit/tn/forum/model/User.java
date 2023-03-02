package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDateTime;
<<<<<<< Updated upstream
import java.util.List;
=======
>>>>>>> Stashed changes

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long userId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;
<<<<<<< Updated upstream
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    private int alertCount;
    private LocalDateTime bannedUntil;
=======
    private int alertCount;
    private Instant bannedUntil;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
>>>>>>> Stashed changes
    @Enumerated(EnumType.STRING)
    public BadgeType postBadge;
    @Enumerated(EnumType.STRING)
    public BadgeType CommentBadge;
<<<<<<< Updated upstream
=======


>>>>>>> Stashed changes
    public boolean isAdmin(User user) {
        return user.getTypeUser().equals("Admin");
    }
    private enum TypeUser {
        Recruter , Admin , Candidate
    }
<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
}
