package com.example.tournamentInvitationService.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TournamentPlayerPageableDto {
   
    private List<TournamentPlayerDto> players;
    private Integer pageSize ;
    private Integer totalItems;
    private Integer totalPages;
    private Integer currentPage;

}
