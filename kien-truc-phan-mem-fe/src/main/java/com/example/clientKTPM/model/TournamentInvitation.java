package com.example.clientKTPM.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TournamentInvitation {
    private Long id;

    private Long tournamentId;

    private Long userId;

    private String type;

    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

}
