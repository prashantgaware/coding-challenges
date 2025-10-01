package com.calculator.services;

import com.calculator.handlers.InvalidExpressionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void testSimpleAddition() {
        assertEquals(5.0, calculatorService.evaluate("2+3"), 0.0001);
    }

    @Test
    void testSimpleSubtraction() {
        assertEquals(5.0, calculatorService.evaluate("8-3"), 0.0001);
    }

    @Test
    void testSimpleMultiplication() {
        assertEquals(12.0, calculatorService.evaluate("3*4"), 0.0001);
    }

    @Test
    void testSimpleDivision() {
        assertEquals(4.0, calculatorService.evaluate("8/2"), 0.0001);
    }

    @Test
    void testDecimalNumbers() {
        assertEquals(5.5, calculatorService.evaluate("2.5+3"), 0.0001);
        assertEquals(7.8, calculatorService.evaluate("3.9*2"), 0.0001);
    }

    @Test
    void testMultipleDecimals() {
        assertEquals(6.25, calculatorService.evaluate("2.5*2.5"), 0.0001);
    }

    @Test
    void testComplexExpression() {
        assertEquals(14.0, calculatorService.evaluate("2+3*4"), 0.0001);
    }

    @Test
    void testParentheses() {
        assertEquals(20.0, calculatorService.evaluate("(2+3)*4"), 0.0001);
    }

    @Test
    void testNestedParentheses() {
        assertEquals(26.0, calculatorService.evaluate("((2+3)*4)+6"), 0.0001);
    }

    @Test
    void testSinFunction() {
        assertEquals(0.5, calculatorService.evaluate("sin(30)"), 0.0001);
    }

    @Test
    void testCosFunction() {
        assertEquals(0.5, calculatorService.evaluate("cos(60)"), 0.0001);
    }

    @Test
    void testTanFunction() {
        assertEquals(1.0, calculatorService.evaluate("tan(45)"), 0.0001);
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> calculatorService.evaluate("5/0"));
    }

    @Test
    void testEmptyExpression() {
        assertThrows(InvalidExpressionException.class, () -> calculatorService.evaluate(""));
    }

    @Test
    void testNullExpression() {
        assertThrows(InvalidExpressionException.class, () -> calculatorService.evaluate(null));
    }

    @Test
    void testMismatchedParentheses() {
        assertThrows(InvalidExpressionException.class, () -> calculatorService.evaluate("(2+3"));
        assertThrows(InvalidExpressionException.class, () -> calculatorService.evaluate("2+3)"));
    }
}
