package com.example.tournamentservice.controller;

import com.example.tournamentservice.DTOs.CreateTournamentRequestDto;
import com.example.tournamentservice.security.JwtTokenProvider;
import com.example.tournamentservice.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController( TournamentService tournamentService ) {
        this.tournamentService = tournamentService;
    }


    @PostMapping("/tournament")
    public ResponseEntity<?> create( @RequestBody CreateTournamentRequestDto createTournamentDto ) {
        return this.tournamentService.create(createTournamentDto);
    }


}