package com.audsat.insurance.service.strategy;

import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.model.Customer;
import com.audsat.insurance.util.Percentage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Component
public class MainDriverAgeRiskStrategy implements RiskStrategy {
    @Override
    public Percentage calculatePercentageRateRisk(Insurance insurance) {
        return Optional.ofNullable(insurance.getCustomer())
                .map(Customer::getDriver)
                .map(driver -> Period.between(driver.getBirthdate(), LocalDate.now()).getYears())
                .map(age -> new Percentage(age < 25 ? 2 : 0))
                .orElse(new Percentage(0));
    }


    @Override
    public String getName() {
        return "MainDriverAge";
    }
}
