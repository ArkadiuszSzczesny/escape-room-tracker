package com.github.arkadiuszszczesny.escaperoomtracker.domain.city;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship.Voivodeship;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
public class City extends BaseUUIDEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voivodeship_id", nullable = false)
    private Voivodeship voivodeship;
}
