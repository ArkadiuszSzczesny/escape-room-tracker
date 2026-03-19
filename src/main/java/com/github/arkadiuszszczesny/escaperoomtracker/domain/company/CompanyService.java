package com.github.arkadiuszszczesny.escaperoomtracker.domain.company;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.company.dto.CompanyResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.company.dto.CreateCompanyRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.AlreadyExistsException;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyResponse> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CompanyResponse getById(UUID id) {
        return toResponse(companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found")));
    }

    @Transactional
    public CompanyResponse create(CreateCompanyRequest request) {
        if (companyRepository.existsByName(request.name())) {
            throw new AlreadyExistsException("Company with this name already exists");
        }

        Company company = new Company();
        company.setName(request.name());
        company.setWebsite(request.website());

        return toResponse(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponse update(UUID id, CreateCompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Company not found"));

        company.setName(request.name());
        company.setWebsite(request.website());

        return toResponse(companyRepository.save(company));
    }

    @Transactional
    public void delete(UUID id) {
        if (!companyRepository.existsById(id)) {
            throw new NotFoundException("Company not found");
        }
        companyRepository.deleteById(id);
    }

    private CompanyResponse toResponse(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getWebsite()
        );
    }
}
