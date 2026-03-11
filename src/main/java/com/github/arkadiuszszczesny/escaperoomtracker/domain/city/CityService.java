package com.github.arkadiuszszczesny.escaperoomtracker.domain.city;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.dto.CityResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.dto.CreateCityRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship.Voivodeship;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship.VoivodeshipRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final VoivodeshipRepository voivodeshipRepository;

    public List<CityResponse> getAll() {
        return cityRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<CityResponse> getByVoivodeship(UUID voivodeshipId) {
        return cityRepository.findByVoivodeshipId(voivodeshipId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CityResponse getById(UUID id) {
        return toResponse(cityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("City not found")));
    }
    @Transactional
    public CityResponse create(CreateCityRequest request) {
        Voivodeship voivodeship = voivodeshipRepository.findById(request.voivodeshipId())
                .orElseThrow(() -> new NotFoundException("Voivodeship not found"));

        City city = new City();
        city.setName(request.name());
        city.setVoivodeship(voivodeship);

        return toResponse(cityRepository.save(city));
    }

    private CityResponse toResponse(City city) {
        return new CityResponse(
                city.getId(),
                city.getName(),
                city.getVoivodeship().getName()
        );
    }
}
