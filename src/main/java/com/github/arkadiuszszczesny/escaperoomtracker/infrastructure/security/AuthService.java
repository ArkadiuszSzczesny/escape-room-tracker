package com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.role.Role;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.role.RoleRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.User;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.UserRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.AlreadyExistsException;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.BusinessException;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security.dto.AuthResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security.dto.LoginRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistsException("Email already in use");
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new AlreadyExistsException("Username already in use");
        }

        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new BusinessException("Default role not found"));

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(role);

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                role.getName()
        );

        return new AuthResponse(token, user.getUsername(), role.getName());
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole().getName()
        );

        return new AuthResponse(token, user.getUsername(), user.getRole().getName());
    }
}
