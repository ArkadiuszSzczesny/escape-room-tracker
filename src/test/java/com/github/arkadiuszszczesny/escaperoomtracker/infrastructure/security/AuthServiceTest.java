package com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.role.Role;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.role.RoleRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.User;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.UserRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.AlreadyExistsException;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security.dto.LoginRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security.dto.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private Role userRole;

    @BeforeEach
    void setUp() {
        userRole = new Role();
        userRole.setId(UUID.randomUUID());
        userRole.setName("ROLE_USER");
    }

    @Test
    void register_shouldReturnToken_whenDataIsValid() {
        // given
        RegisterRequest request = new RegisterRequest(
                "testuser", "test@test.com", "password123"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(userRepository.existsByUsername(request.username())).thenReturn(false);
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> {
            User u = i.getArgument(0);
            u.setId(UUID.randomUUID());
            return u;
        });
        when(jwtService.generateToken(any(), any(), any())).thenReturn("token123");

        // when
        var response = authService.register(request);

        // then
        assertThat(response.token()).isEqualTo("token123");
        assertThat(response.username()).isEqualTo("testuser");
        assertThat(response.role()).isEqualTo("ROLE_USER");
    }

    @Test
    void register_shouldThrowAlreadyExistsException_whenEmailTaken() {
        // given
        RegisterRequest request = new RegisterRequest(
                "testuser", "test@test.com", "password123"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        // when & then
        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("Email already in use");

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        // given
        LoginRequest request = new LoginRequest("test@test.com", "password123");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@test.com");
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setRole(userRole);

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.password(), "encodedPassword")).thenReturn(true);
        when(jwtService.generateToken(any(), any(), any())).thenReturn("token123");

        // when
        var response = authService.login(request);

        // then
        assertThat(response.token()).isEqualTo("token123");
        assertThat(response.username()).isEqualTo("testuser");
    }
}
