package com.example.tournamentInvitationService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateInvitation {
    private Long tournamentId;
    private Long userId;
    private String type;
}
