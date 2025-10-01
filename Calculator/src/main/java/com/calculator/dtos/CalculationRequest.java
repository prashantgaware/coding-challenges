package com.calculator.dtos;

import jakarta.validation.constraints.NotBlank;

public class CalculationRequest {
    @NotBlank
    public String expression;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
