package com.audsat.insurance.service.strategy;

import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.model.Car;
import com.audsat.insurance.util.Percentage;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarClaimHistoryRiskStrategy implements RiskStrategy {
    @Override
    public Percentage calculatePercentageRateRisk(Insurance insurance) {
        return Optional.ofNullable(insurance.getCar())
                .map(Car::getClaims).filter(claims -> !claims.isEmpty()).map(claims -> new Percentage(2))
                .orElse(new Percentage(0));
    }

    @Override
    public String getName() {
        return "CarClaimHistory";
    }
}
