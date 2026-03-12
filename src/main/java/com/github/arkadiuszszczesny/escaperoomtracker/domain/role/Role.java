package com.github.arkadiuszszczesny.escaperoomtracker.domain.role;

import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role extends BaseUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;
}