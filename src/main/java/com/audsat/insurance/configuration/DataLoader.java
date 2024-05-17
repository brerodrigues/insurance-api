package com.audsat.insurance.configuration;

import com.audsat.insurance.model.*;
import com.audsat.insurance.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(
            CarRepository carRepository,
            DriverRepository driverRepository,
            ClaimRepository claimRepository,
            CarDriverRepository carDriverRepository,
            CustomerRepository customerRepository,
            InsuranceRepository insuranceRepository) {
        return args -> {
            // Insert Car data
            Car tesla = carRepository.save(new Car(null, "Model S", "Tesla", 2020,
                    80000.0, null, null));
            Car ford = carRepository.save(new Car(null, "Mustang", "Ford", 2018,
                    30000.0, null, null));

            // Insert Driver data
            Driver driverOne = driverRepository.save(new Driver(null, "123456789",
                    LocalDate.of(1994, 5, 15), null));
            Driver driverTwo = driverRepository.save(new Driver(null, "987654321",
                    LocalDate.of(2005, 8, 20), null));

            // Insert Claims data
            claimRepository.save(new Claim(null, tesla, driverOne, LocalDate.now().minusDays(10)));

            // Insert CarDriver data
            carDriverRepository.save(new CarDriver(null, tesla, driverOne, true));
            carDriverRepository.save(new CarDriver(null, ford, driverTwo, false));

            // Insert Customer data
            Customer customerOne = customerRepository.save(new Customer(null,
                    "John Doe", driverOne, null));
            Customer customerTwo = customerRepository.save(new Customer(null,
                    "Jane Smith", driverTwo, null));

            // Insert Insurance data
            insuranceRepository.save(new Insurance(null, customerOne, tesla, 1200.00, 0.05,
                    LocalDateTime.now(), null, true));
            insuranceRepository.save(new Insurance(null, customerTwo, ford, 650.00, 0.03,
                    LocalDateTime.now(), null, true));
        };
    }
}
