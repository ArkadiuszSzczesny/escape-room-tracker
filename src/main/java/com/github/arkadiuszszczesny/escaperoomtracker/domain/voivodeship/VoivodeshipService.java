package com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship.dto.VoivodeshipResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoivodeshipService {

    private final VoivodeshipRepository voivodeshipRepository;

    public List<VoivodeshipResponse> getAll() {
        return voivodeshipRepository.findAll()
                .stream()
                .map(v -> new VoivodeshipResponse(v.getId(), v.getName()))
                .toList();
    }

    public VoivodeshipResponse getById(UUID id) {
        Voivodeship v = voivodeshipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Voivodeship not found"));
        return new VoivodeshipResponse(v.getId(), v.getName());
    }
}
