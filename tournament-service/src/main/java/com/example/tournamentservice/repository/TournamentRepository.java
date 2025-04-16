package com.example.tournamentservice.repository;

import com.example.tournamentservice.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    boolean existsByName(String name);

    List<Tournament> findByNameContaining(String username);

    List<Tournament> findByDeletedAtIsNull();
}