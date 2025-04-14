package com.example.tournamentservice.service;

import com.example.tournamentservice.DTOs.CreateTournamentRequestDto;
import com.example.tournamentservice.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public ResponseEntity<?> create( CreateTournamentRequestDto createTournamentDto){
        return ResponseEntity.ok(createTournamentDto);
    }
}