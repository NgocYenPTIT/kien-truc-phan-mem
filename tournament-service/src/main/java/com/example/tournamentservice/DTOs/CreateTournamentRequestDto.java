package com.example.tournamentservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateTournamentRequestDto {
    private String name;

    private String description;

    private Long organizerId;

    private Long boardTypeId;

    private Long organizingMethodId;

    private Integer maxPlayer;

    private Date startDate;

    private Date endDate;

}
