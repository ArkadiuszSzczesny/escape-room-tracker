package com.github.arkadiuszszczesny.escaperoomtracker.domain.city;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.dto.CityResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.dto.CreateCityRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<CityResponse> getAll() {
        return cityService.getAll();
    }

    @GetMapping("/{id}")
    public CityResponse getById(@PathVariable UUID id) {
        return cityService.getById(id);
    }

    @GetMapping("/by-voivodeship/{voivodeshipId}")
    public List<CityResponse> getByVoivodeship(@PathVariable UUID voivodeshipId) {
        return cityService.getByVoivodeship(voivodeshipId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse create(@RequestBody @Valid CreateCityRequest request) {
        return cityService.create(request);
    }
}
