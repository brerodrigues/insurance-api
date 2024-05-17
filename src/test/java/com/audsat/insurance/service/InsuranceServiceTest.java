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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InsuranceServiceTest {

    @Mock
    private InsuranceRepository insuranceRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private RiskStrategy riskStrategy1;

    @Mock
    private RiskStrategy riskStrategy2;

    @InjectMocks
    private InsuranceService insuranceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        insuranceService = new InsuranceService(insuranceRepository, List.of(riskStrategy1, riskStrategy2),
                carRepository, customerRepository);
    }

    @Test
    void shouldCreateInsurance() {
        Customer customer = new Customer(1L, "CustomerName", null, null);
        Car car = new Car(1L, "Model", "Manufacturer", 2020, 30000.0, null, null);

        InsuranceDTO insuranceDTO = new InsuranceDTO();
        insuranceDTO.setCustomerId(customer.getId());
        insuranceDTO.setCarId(car.getId());

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(riskStrategy1.calculatePercentageRateRisk(any(Insurance.class))).thenReturn(new Percentage(2));
        when(riskStrategy2.calculatePercentageRateRisk(any(Insurance.class))).thenReturn(new Percentage(0));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(i -> i.getArguments()[0]);

        InsuranceDTO createdInsuranceDTO = insuranceService.createInsurance(insuranceDTO);

        assertNotNull(createdInsuranceDTO);
        assertEquals(customer.getId(), createdInsuranceDTO.getCustomerId());
        assertEquals(car.getId(), createdInsuranceDTO.getCarId());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void shouldReturnInsuranceById() {
        Insurance insurance = new Insurance(1L, new Customer(), new Car(),
                1000.0, 0.6, LocalDateTime.now(), null, true);

        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance));

        Optional<InsuranceDTO> insuranceDTO = insuranceService.getInsurance(1L);

        assertTrue(insuranceDTO.isPresent());
        assertEquals(insurance.getId(), insuranceDTO.get().getId());
    }

    @Test
    void shouldThrowExceptionWhenInsuranceNotFound() {
        when(insuranceRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            insuranceService.getInsurance(1L);
        });
        assertEquals("Insurance id: 1 not found", exception.getMessage());
    }

    @Test
    void shouldUpdateInsurance() {
        Customer customer = new Customer(1L, "CustomerName", null, null);
        Car car = new Car(1L, "Model", "Manufacturer", 2020, 30000.0, null, null);
        Insurance existingInsurance = new Insurance(1L, customer, car, 1000.0, 0.6,
                LocalDateTime.now(), null, true);

        InsuranceDTO insuranceDTO = new InsuranceDTO(existingInsurance);
        insuranceDTO.setCustomerId(customer.getId());
        insuranceDTO.setCarId(car.getId());

        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(existingInsurance));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        when(riskStrategy1.calculatePercentageRateRisk(any(Insurance.class))).thenReturn(new Percentage(2));
        when(riskStrategy2.calculatePercentageRateRisk(any(Insurance.class))).thenReturn(new Percentage(0));
        when(insuranceRepository.save(any(Insurance.class))).thenAnswer(i -> i.getArguments()[0]);

        InsuranceDTO updatedInsuranceDTO = insuranceService.updateInsurance(1L, insuranceDTO);

        assertNotNull(updatedInsuranceDTO);
        assertEquals(existingInsurance.getId(), updatedInsuranceDTO.getId());
        verify(insuranceRepository, times(1)).save(any(Insurance.class));
    }

    @Test
    void shouldDeleteInsuranceById() {
        Insurance insurance = new Insurance(1L, new Customer(), new Car(), 1000.0, 0.6, LocalDateTime.now(), null, true);
        when(insuranceRepository.findById(1L)).thenReturn(Optional.of(insurance));

        insuranceService.deleteInsurance(1L);

        verify(insuranceRepository, times(1)).delete(insurance);
    }

    @Test
    void shouldThrowExceptionWhenTryToDeleteInsuranceThatDontExists() {
        when(insuranceRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            insuranceService.deleteInsurance(1L);
        });
        assertEquals("Insurance id: 1 not found", exception.getMessage());
    }
}
