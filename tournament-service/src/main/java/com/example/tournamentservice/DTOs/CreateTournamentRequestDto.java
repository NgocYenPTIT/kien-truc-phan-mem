package com.example.tournamentservice.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTournamentRequestDto {
    private String name;
    private String description;
    private Long organizerId;
    private Long boardTypeId;       // ID tham chiếu đến BoardType
    private Long organizingMethodId; // ID tham chiếu đến OrganizingMethod
    private Integer maxPlayer;
    private String startDate;  // Kiểu String thay vì Date
    private String endDate;    // Kiểu String thay vì Date
}