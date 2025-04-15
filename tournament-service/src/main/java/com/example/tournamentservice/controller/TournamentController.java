package com.example.tournamentservice.controller;

import com.example.tournamentservice.DTOs.TournamentDto;
import com.example.tournamentservice.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController( TournamentService tournamentService ) {
        this.tournamentService = tournamentService;
    }


    @PostMapping("/tournament")
    public ResponseEntity<?> create(@RequestBody TournamentDto createTournamentDto , HttpServletRequest request) {
        return this.tournamentService.create(createTournamentDto, request);
    }

    @GetMapping("/tournament/{id}")
    public ResponseEntity<?> getDetail(@PathVariable("id") Long id , HttpServletRequest request) {
        return this.tournamentService.getDetail(id, request);
    }


}