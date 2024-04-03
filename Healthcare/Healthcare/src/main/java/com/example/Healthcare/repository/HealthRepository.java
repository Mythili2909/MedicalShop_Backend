package com.example.Healthcare.repository;

import com.example.Healthcare.model.HealthModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<HealthModel, Long> {
    Optional<HealthModel> findByEmail(String email);
    void deleteByEmail(String email);
}
