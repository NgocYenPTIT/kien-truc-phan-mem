package com.example.tournamentservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTournamentRequestDto {
    private String name;
    private String description;
    private boolean freeToJoin;
    @NotNull
    private Long boardTypeId;
    @NotNull
    private Long organizingMethodId; // ID tham chiếu đến OrganizingMethod
    private Integer maxPlayer;
    private String startDate;  // Kiểu String thay vì Date
    private String endDate;    // Kiểu String thay vì Date
}