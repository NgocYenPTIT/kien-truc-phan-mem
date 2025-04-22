package com.example.clientKTPM.model ;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentResponse extends TournamentDto {
    private ArrayList<TournamentRoundDto> round;
}