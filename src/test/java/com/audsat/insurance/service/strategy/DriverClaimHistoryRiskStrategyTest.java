package com.audsat.insurance.service.strategy;

import com.audsat.insurance.model.Claim;
import com.audsat.insurance.model.Customer;
import com.audsat.insurance.model.Driver;
import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.util.Percentage;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DriverClaimHistoryRiskStrategyTest {

    @Test
    void shouldReturnZeroPercentageWhenDriverHasNoClaims() {
        // Arrange
        Insurance insurance = new Insurance();
        Customer customer = new Customer();
        Driver driver = new Driver();
        driver.setClaims(Collections.emptyList());
        customer.setDriver(driver);
        insurance.setCustomer(customer);

        RiskStrategy riskStrategy = new DriverClaimHistoryRiskStrategy();

        Percentage percentage = riskStrategy.calculatePercentageRateRisk(insurance);

        assertEquals(0, percentage.getValue());
    }

    @Test
    void shouldReturnTwoPercentageWhenDriverHasClaims() {
        Insurance insurance = new Insurance();
        Customer customer = new Customer();
        Driver driver = new Driver();
        driver.setClaims(Collections.singletonList(new Claim()));
        customer.setDriver(driver);
        insurance.setCustomer(customer);

        RiskStrategy riskStrategy = new DriverClaimHistoryRiskStrategy();

        Percentage percentage = riskStrategy.calculatePercentageRateRisk(insurance);

        assertEquals(2, percentage.getValue());
    }
}
