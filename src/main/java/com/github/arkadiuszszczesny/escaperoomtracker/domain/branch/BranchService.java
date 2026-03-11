package com.github.arkadiuszszczesny.escaperoomtracker.domain.branch;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.dto.BranchResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.dto.CreateBranchRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.City;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.CityRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.company.Company;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.company.CompanyRepository;
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
public class BranchService {

    private final BranchRepository branchRepository;
    private final CompanyRepository companyRepository;
    private final CityRepository cityRepository;

    public List<BranchResponse> getAll() {
        return branchRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BranchResponse> getByCompany(UUID companyId) {
        return branchRepository.findByCompanyId(companyId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BranchResponse getById(UUID id) {
        return toResponse(branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found")));
    }

    @Transactional
    public BranchResponse create(CreateBranchRequest request) {

        if (branchRepository.existsByCompanyIdAndAddress(request.companyId(), request.address())) {
            throw new AlreadyExistsException("Branch for this company at this address already exists");
        }

        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new NotFoundException("Company not found"));

        City city = cityRepository.findById(request.cityId())
                .orElseThrow(() -> new NotFoundException("City not found"));

        Branch branch = new Branch();
        branch.setCompany(company);
        branch.setCity(city);
        branch.setAddress(request.address());

        return toResponse(branchRepository.save(branch));
    }

    @Transactional
    public void delete(UUID id) {
        if (!branchRepository.existsById(id)) {
            throw new NotFoundException("Branch not found");
        }
        branchRepository.deleteById(id);
    }

    private BranchResponse toResponse(Branch branch) {
        return new BranchResponse(
                branch.getId(),
                branch.getAddress(),
                branch.getCompany().getName(),
                branch.getCity().getName(),
                branch.getCity().getVoivodeship().getName()
        );
    }
}
