package com.github.arkadiuszszczesny.escaperoomtracker.domain.company;

import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
public class Company extends BaseUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String website;
}
