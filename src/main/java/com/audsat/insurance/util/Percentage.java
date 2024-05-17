package com.audsat.insurance.util;

public class Percentage {
    private final int value;

    public Percentage(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Percentage value must be between 0 and 100");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public double toDecimal() {
        return value / 100.0;
    }
}
