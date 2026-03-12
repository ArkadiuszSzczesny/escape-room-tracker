package com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship;

import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "voivodeships")
@Data
@NoArgsConstructor
public class Voivodeship extends BaseUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
