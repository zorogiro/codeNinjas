package com.esprit.tn.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private com.esprit.tn.forum.model.TypeUser typeUser;
    private Long universityId;

}
