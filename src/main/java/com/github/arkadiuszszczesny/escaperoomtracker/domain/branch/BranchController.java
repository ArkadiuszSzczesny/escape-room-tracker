package com.github.arkadiuszszczesny.escaperoomtracker.domain.branch;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.dto.BranchResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.dto.CreateBranchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public List<BranchResponse> getAll() {
        return branchService.getAll();
    }

    @GetMapping("/{id}")
    public BranchResponse getById(@PathVariable UUID id) {
        return branchService.getById(id);
    }

    @GetMapping("/by-company/{companyId}")
    public List<BranchResponse> getByCompany(@PathVariable UUID companyId) {
        return branchService.getByCompany(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BranchResponse create(@RequestBody @Valid CreateBranchRequest request) {
        return branchService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        branchService.delete(id);
    }
}
