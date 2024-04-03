package com.example.Healthcare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import @Transactional annotation

import com.example.Healthcare.model.HealthModel;
import com.example.Healthcare.repository.HealthRepository;

@Service
@Transactional // Add @Transactional annotation at the class level
public class HealthService {

    private final HealthRepository healthRepository;

    public HealthService(HealthRepository healthRepository) {
        this.healthRepository = healthRepository;
    }

    public HealthModel saveHealthData(HealthModel healthModel) {
        return healthRepository.save(healthModel);
    }

    public List<HealthModel> getAllHealthData() {
        return healthRepository.findAll();
    }

    public Optional<HealthModel> getHealthDataById(Long id) {
        return healthRepository.findById(id);
    }

    public HealthModel updateHealthData(Long id, HealthModel updatedData) {
        if (healthRepository.existsById(id)) {
            updatedData.setId(id);
            return healthRepository.save(updatedData);
        }
        return null; // Or throw an exception indicating the record doesn't exist
    }

    @Transactional // Add @Transactional annotation at the method level
    public void deleteHealthData(Long id) {
        healthRepository.deleteById(id);
    }

    @Transactional // Add @Transactional annotation at the method level
    public void deleteHealthDataByEmail(String email) {
        healthRepository.deleteByEmail(email);
    }

    public HealthModel updateHealthcareByEmail(String email, HealthModel healthcare) {
        Optional<HealthModel> existingHealthcare = healthRepository.findByEmail(email);
        if (existingHealthcare.isPresent()) {
            healthcare.setId(existingHealthcare.get().getId());
            return healthRepository.save(healthcare);
        } else {
            return null;
        }
    }

    public Optional<HealthModel> getHealthcareByEmail(String email) {
        return healthRepository.findByEmail(email);
    }
}
