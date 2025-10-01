package com.calculator.dtos;

public class CalculationResponse {
    public double result;

    public CalculationResponse(double result) {
        this.result = result;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
