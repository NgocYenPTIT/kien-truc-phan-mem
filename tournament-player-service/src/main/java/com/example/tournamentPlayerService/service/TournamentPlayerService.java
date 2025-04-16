package com.example.tournamentPlayerService.service;

import com.example.tournamentPlayerService.DTOs.TournamentPlayerDto;
import com.example.tournamentPlayerService.model.TournamentPlayer;
import com.example.tournamentPlayerService.repository.TournamentPlayerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Service
public class TournamentPlayerService {
    @Autowired
    private TournamentPlayerRepository tournamentPlayerRepository;

    public ResponseEntity<?> getListPlayers(Long id) {
        List<TournamentPlayer> players = this.tournamentPlayerRepository.findByTournamentIdAndDeletedAtIsNull(id);

        List<TournamentPlayerDto> playerDtos = players.stream()
                .map(player -> new TournamentPlayerDto(
                        player.getId(),
                        player.getTournamentId(),
                        player.getPlayerId(),
                        player.getStatus(),
                        player.getCreatedAt(),
                        player.getUpdatedAt(),
                        player.getDeletedAt()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(playerDtos);
    }
}