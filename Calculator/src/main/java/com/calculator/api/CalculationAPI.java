package com.calculator.api;

import com.calculator.dtos.CalculationRequest;
import com.calculator.dtos.CalculationResponse;
import com.calculator.services.CalculatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalculationAPI {

    @Autowired
    private CalculatorService calculatorService;

    /**
     * Handles POST requests to /api/calculate.
     * @param request The request body containing the expression.
     * @return A response entity with the calculation result.
     */
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@Valid @RequestBody CalculationRequest request) {
        double result = calculatorService.evaluate(request.getExpression());
        return ResponseEntity.ok(new CalculationResponse(result));
    }
}
