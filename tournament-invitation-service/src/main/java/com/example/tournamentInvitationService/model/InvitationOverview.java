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
public class InvitationOverview {
    private  Tournament tournament;
    private User participant;
    private Date createdAt;
//    private String status;
}
