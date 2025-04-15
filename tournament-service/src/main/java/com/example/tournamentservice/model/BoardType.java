package com.example.tournamentservice.model;

import javax.persistence.*;

import lombok.*;

import java.util.List;

@Entity
@Table(name = "board_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String size;

    private String description;

    private Boolean isActive;


}