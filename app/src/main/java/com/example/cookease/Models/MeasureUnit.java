package com.example.cookease.Models;

public class MeasureUnit {
    private final double amount;
    private final String unitShort;

    public MeasureUnit(double amount, String unitShort) {
        this.amount = amount;
        this.unitShort = unitShort;
    }

    public double getAmount() { return amount; }
    public String getUnitShort() { return unitShort; }
}
