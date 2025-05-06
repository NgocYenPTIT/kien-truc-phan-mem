package com.example.tournamentInvitationService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TournamentPlayer {
//    private List<TournamentPlayerDto> players;
    private Integer pageSize ;
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;
}
