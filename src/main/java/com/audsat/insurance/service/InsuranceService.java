package com.audsat.insurance.service;

import com.audsat.insurance.dto.InsuranceDTO;
import com.audsat.insurance.model.Car;
import com.audsat.insurance.model.Customer;
import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.repository.CarRepository;
import com.audsat.insurance.repository.CustomerRepository;
import com.audsat.insurance.repository.InsuranceRepository;
import com.audsat.insurance.service.strategy.RiskStrategy;
import com.audsat.insurance.util.Percentage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;
    private final List<RiskStrategy> riskStrategies;

    public InsuranceService(InsuranceRepository insuranceRepository, List<RiskStrategy> riskStrategies,
                            CarRepository carRepository, CustomerRepository customerRepository) {
        this.insuranceRepository = insuranceRepository;
        this.riskStrategies = riskStrategies;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    public InsuranceDTO createInsurance(InsuranceDTO insuranceDTO) {
        Customer customer = getCustomer(insuranceDTO.getCustomerId());
        Car car = getCar(insuranceDTO.getCarId());

        Insurance insurance = new Insurance();
        insurance.setCustomer(customer);
        insurance.setCar(car);
        insurance.setPercentageRate(calculatePercentageRate(insurance));
        insurance.setQuote(calculateQuote(car, insurance.getPercentageRate()));
        insurance.setCreationDate(LocalDateTime.now());
        insurance.setIsActive(true);

        return new InsuranceDTO(insuranceRepository.save(insurance));
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Customer id: %s not found", customerId)));
    }

    private Car getCar(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Car id: %s not found", carId)));
    }

    private double calculatePercentageRate(Insurance insurance) {
        double baseRate = new Percentage(6).toDecimal();

        double additionalRate = riskStrategies.stream()
                .map(strategy -> strategy.calculatePercentageRateRisk(insurance).toDecimal())
                .mapToDouble(Double::doubleValue)
                .sum();

        return baseRate + additionalRate;
    }

    private double calculateQuote(Car car, double rate) {
        return car.getFipeValue() * rate;
    }

    public Optional<InsuranceDTO> getInsurance(Long id) {
        return insuranceRepository.findById(id)
                .map(InsuranceDTO::new)
                .or(() -> {
                    throw new EntityNotFoundException(String.format("Insurance id: %s not found", id));
                });
    }

    public InsuranceDTO updateInsurance(Long id, InsuranceDTO insuranceDTO) {
        Insurance insurance = getInsuranceById(id);
        Customer customer = getCustomer(insuranceDTO.getCustomerId());
        Car car = getCar(insuranceDTO.getCarId());

        insurance.setCustomer(customer);
        insurance.setCar(car);
        insurance.setPercentageRate(calculatePercentageRate(insurance));
        insurance.setQuote(calculateQuote(car, insurance.getPercentageRate()));
        insurance.setUpdatedAt(LocalDateTime.now());

        return new InsuranceDTO(insuranceRepository.save(insurance));
    }

    private Insurance getInsuranceById(Long id) {
        return insuranceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Insurance id: %s not found", id)));
    }

    public void deleteInsurance(Long id) {
        Insurance insurance = getInsuranceById(id);
        insuranceRepository.delete(insurance);
    }
}
