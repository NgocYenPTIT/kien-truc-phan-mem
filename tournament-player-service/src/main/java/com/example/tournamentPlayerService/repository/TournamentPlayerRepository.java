package com.example.tournamentPlayerService.repository;

import com.example.tournamentPlayerService.model.TournamentPlayer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentPlayerRepository extends JpaRepository<TournamentPlayer, Long> {
    List<TournamentPlayer> findByTournamentIdAndDeletedAtIsNull(Long id);
}
