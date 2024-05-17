package com.audsat.insurance.service.strategy;

import com.audsat.insurance.model.Customer;
import com.audsat.insurance.model.Driver;
import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.util.Percentage;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainDriverAgeRiskStrategyTest {

    @Test
    void shouldReturnTwoPercentWhenDriverHasLessThan20YearsOld() {
        Insurance insurance = new Insurance();
        Customer customer = new Customer();
        Driver driver = new Driver();
        driver.setBirthdate(LocalDate.now().minusYears(20));
        customer.setDriver(driver);
        insurance.setCustomer(customer);

        RiskStrategy riskStrategy = new MainDriverAgeRiskStrategy();

        Percentage percentage = riskStrategy.calculatePercentageRateRisk(insurance);

        assertEquals(2, percentage.getValue());
    }

    @Test
    void shouldReturnZeroPercentWhenDriverHasMoreThan25YearsOld() {
        Insurance insurance = new Insurance();
        Customer customer = new Customer();
        Driver driver = new Driver();
        driver.setBirthdate(LocalDate.now().minusYears(30));
        customer.setDriver(driver);
        insurance.setCustomer(customer);

        RiskStrategy riskStrategy = new MainDriverAgeRiskStrategy();

        Percentage percentage = riskStrategy.calculatePercentageRateRisk(insurance);

        assertEquals(0, percentage.getValue());
    }
}
