package com.example.tournamentservice.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "board_types")
@Data
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

    // Quan hệ OneToMany với Tournament
    @OneToMany(mappedBy = "boardType", cascade = CascadeType.ALL)
    private List<Tournament> tournaments;
}