package com.github.arkadiuszszczesny.escaperoomtracker.domain.user;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.role.Role;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends BaseUUIDEntity {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
