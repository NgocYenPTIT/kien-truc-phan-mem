package com.example.tournamentInvitationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private Date dateOfBirth;
    private boolean gender;
    private String email;
    private String phoneNumber;
    private boolean isActive ;
    private Date lastAccess;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
