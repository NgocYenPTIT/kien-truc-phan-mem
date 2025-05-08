package com.example.tournamentInvitationService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TournamentPlayer {
    private Integer pageSize ;
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;
}
