package com.example.tournamentInvitationService.controller;

import com.example.tournamentInvitationService.model.CreateInvitation;
import com.example.tournamentInvitationService.model.TournamentInvitation;
import com.example.tournamentInvitationService.service.TournamentInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TournamentInvitationController {

    private final TournamentInvitationService tournamentInvitationService;

    TournamentInvitationController(TournamentInvitationService tournamentInvitationService) {
        this.tournamentInvitationService = tournamentInvitationService;
    }

    @PostMapping("/invitation")
    public ResponseEntity<?> create(HttpServletRequest request, CreateInvitation invitation) {
        TournamentInvitation tournamentInvitation = tournamentInvitationService.create(request, invitation);

        if(tournamentInvitation == null) return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(tournamentInvitation);
    }

    @GetMapping("/invitation")
    public ResponseEntity<?> getList(HttpServletRequest request) {
        return ResponseEntity.ok(tournamentInvitationService.getList(request));
    }

    @PutMapping("/invitation")
    public ResponseEntity<?> update(HttpServletRequest request, CreateInvitation invitation) {
        return ResponseEntity.ok(tournamentInvitationService.update( request, invitation));
    }
}