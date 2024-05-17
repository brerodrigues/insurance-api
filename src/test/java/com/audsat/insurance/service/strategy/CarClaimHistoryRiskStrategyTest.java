package com.audsat.insurance.service.strategy;

import com.audsat.insurance.model.Car;
import com.audsat.insurance.model.Claim;
import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.util.Percentage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarClaimHistoryRiskStrategyTest {

    @Test
    void shouldReturnZeroAsPercentageWhenCarDontHaveClaims() {
        Insurance insurance = mock(Insurance.class);
        Car car = new Car();

        when(insurance.getCar()).thenReturn(car);

        CarClaimHistoryRiskStrategy strategy = new CarClaimHistoryRiskStrategy();

        Percentage percentage = strategy.calculatePercentageRateRisk(insurance);

        assertEquals(0, percentage.getValue());
    }

    @Test
    void shouldReturnTwoAsPercentageWhenCarHaveClaims() {
        Insurance insurance = mock(Insurance.class);
        Car car = new Car();
        car.setClaims(List.of(new Claim()));

        when(insurance.getCar()).thenReturn(car);

        CarClaimHistoryRiskStrategy strategy = new CarClaimHistoryRiskStrategy();

        Percentage percentage = strategy.calculatePercentageRateRisk(insurance);

        assertEquals(2, percentage.getValue());
    }
}
