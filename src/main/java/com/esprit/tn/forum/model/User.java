package com.esprit.tn.forum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

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
    private  String cin;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant created;
    private boolean enabled;
    private int alertCount;
    private Instant bannedUntil;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;
    @Enumerated(EnumType.STRING)
    public BadgeType postBadge;
    @Enumerated(EnumType.STRING)
    public BadgeType CommentBadge;


    public boolean isAdmin() {
        return getTypeUser().equals(TypeUser.Admin);
    }

    public boolean isCandidate() {
        return getTypeUser().equals(TypeUser.Candidate);
    }

    public boolean isRecruter(User user) {
        return getTypeUser().equals(TypeUser.Recruiter);
    }


}
