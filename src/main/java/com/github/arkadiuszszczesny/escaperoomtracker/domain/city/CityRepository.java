package com.github.arkadiuszszczesny.escaperoomtracker.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findByVoivodeshipId(UUID voivodeshipId);
    boolean existsByNameAndVoivodeshipId(String name, UUID voivodeshipId);
}