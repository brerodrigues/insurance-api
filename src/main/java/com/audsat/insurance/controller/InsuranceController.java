package com.audsat.insurance.controller;

import com.audsat.insurance.dto.InsuranceDTO;
import com.audsat.insurance.service.InsuranceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/insurance/budget")
public class InsuranceController {

    private InsuranceService insuranceService;

    public InsuranceController(InsuranceService insuranceService) {
        this.insuranceService = insuranceService;
    }

    @PostMapping
    public ResponseEntity<InsuranceDTO> createInsurance(@RequestBody InsuranceDTO insuranceDTO) {
        InsuranceDTO createdInsurance = insuranceService.createInsurance(insuranceDTO);
        return ResponseEntity.ok(createdInsurance);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceDTO> getInsurance(@PathVariable Long id) {
        Optional<InsuranceDTO> insurance = insuranceService.getInsurance(id);
        return insurance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceDTO> updateInsurance(@PathVariable Long id, @RequestBody InsuranceDTO insuranceDetails) {
        InsuranceDTO updatedInsurance = insuranceService.updateInsurance(id, insuranceDetails);
        return ResponseEntity.ok(updatedInsurance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInsurance(@PathVariable Long id) {
        insuranceService.deleteInsurance(id);
        return ResponseEntity.ok().build();
    }
}
