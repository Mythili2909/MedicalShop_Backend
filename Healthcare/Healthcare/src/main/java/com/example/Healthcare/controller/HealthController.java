package com.example.Healthcare.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Healthcare.model.HealthModel;
import com.example.Healthcare.service.HealthService;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @PostMapping("/add")
    public String addHealthRecord(@RequestBody HealthModel healthModel) {
        HealthModel savedHealthModel = healthService.saveHealthData(healthModel);
        if (savedHealthModel != null) {
            return "Health record added successfully.";
        } else {
            return "Email already exists. Please use a different email.";
        }
    }

    @GetMapping("/all")
    public List<HealthModel> getAllHealthRecords() {
        return healthService.getAllHealthData();
    }

    @GetMapping("/id/{id}")
    public Optional<HealthModel> getHealthRecordById(@PathVariable Long id) {
        return healthService.getHealthDataById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<HealthModel> getHealthcareByEmail(@PathVariable String email) {
        Optional<HealthModel> healthcare = healthService.getHealthcareByEmail(email);
        return healthcare.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/id/{id}")
    public HealthModel updateHealthRecord(@PathVariable Long id, @RequestBody HealthModel updatedData) {
        return healthService.updateHealthData(id, updatedData);
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<HealthModel> updateHealthcareByEmail(@PathVariable String email, @RequestBody HealthModel healthcare) {
        Optional<HealthModel> existingHealthcare = healthService.getHealthcareByEmail(email);
        if (existingHealthcare.isPresent()) {
            HealthModel updatedHealthcare = healthService.updateHealthcareByEmail(email, healthcare);
            return new ResponseEntity<>(updatedHealthcare, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{id}")
    public void deleteHealthRecord(@PathVariable Long id) {
        healthService.deleteHealthData(id);
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteHealthDataByEmail(@PathVariable String email) {
        Optional<HealthModel> existingHealthcare = healthService.getHealthcareByEmail(email);
        if (existingHealthcare.isPresent()) {
            healthService.deleteHealthDataByEmail(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
