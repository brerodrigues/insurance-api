package com.audsat.insurance.service.strategy;

import com.audsat.insurance.model.Insurance;
import com.audsat.insurance.util.Percentage;

public interface RiskStrategy {
    Percentage calculatePercentageRateRisk(Insurance insurance);
    String getName();
}
