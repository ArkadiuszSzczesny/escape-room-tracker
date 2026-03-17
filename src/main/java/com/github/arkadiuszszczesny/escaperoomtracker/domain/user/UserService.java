package com.github.arkadiuszszczesny.escaperoomtracker.domain.user;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.dto.UserResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getMe(UUID userId) {
        return toResponse(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    public UserResponse getById(UUID id) {
        return toResponse(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    public Page<UserResponse> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(this::toResponse);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }
}