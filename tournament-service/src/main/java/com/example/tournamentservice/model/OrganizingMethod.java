package com.example.tournamentservice.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "organizing_methods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizingMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer size;

    private String description;

    // Quan hệ OneToMany với Tournament
    @OneToMany(mappedBy = "organizingMethod", cascade = CascadeType.ALL)
    private List<Tournament> tournaments;
}