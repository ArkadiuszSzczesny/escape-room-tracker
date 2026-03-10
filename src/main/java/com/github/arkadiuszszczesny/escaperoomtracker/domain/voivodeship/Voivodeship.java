package com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "voivodeships")
@Data
@NoArgsConstructor
public class Voivodeship {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;
}
