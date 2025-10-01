package com.calculator.services;

import com.calculator.handlers.InvalidExpressionException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalculatorService {

    private static final Map<String, Integer> PRECEDENCE = new HashMap<>();
    static {
        PRECEDENCE.put("+", 1);
        PRECEDENCE.put("-", 1);
        PRECEDENCE.put("*", 2);
        PRECEDENCE.put("/", 2);
        PRECEDENCE.put("sin", 3);
        PRECEDENCE.put("cos", 3);
        PRECEDENCE.put("tan", 3);
    }

    /**
     * The main public method to evaluate a mathematical expression.
     * @param expression The infix mathematical expression string.
     * @return The result of the calculation.
     */
    public double evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new InvalidExpressionException("Expression cannot be empty.");
        }
        List<String> tokens = tokenize(expression);
        List<String> postfix = infixToPostfix(tokens);
        return evaluatePostfix(postfix);
    }

    /**
     * Step 1: Tokenize the input string into numbers, operators, and parentheses.
     * This uses a regular expression to find all matching parts.
     * @param expression The input string.
     * @return A list of tokens.
     */
    private List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        // Regex to find numbers (including decimals), operators, parentheses, and functions
        // Note: We match numbers without leading minus to handle subtraction correctly
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*|[+\\-*/()]|sin|cos|tan");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    /**
     * Step 2 (Part A): Converts a list of infix tokens to postfix (RPN)
     * using the Shunting-yard algorithm.
     * @param infixTokens The list of tokens in infix order.
     * @return The list of tokens in postfix order.
     */
    private List<String> infixToPostfix(List<String> infixTokens) {
        List<String> outputQueue = new ArrayList<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        for (String token : infixTokens) {
            if (isNumber(token)) {
                outputQueue.add(token);
            } else if (isFunction(token)) {
                operatorStack.push(token);
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek()) &&
                        PRECEDENCE.get(operatorStack.peek()) >= PRECEDENCE.get(token)) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else if (token.equals("(")) {
                operatorStack.push(token);
            } else if (token.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    outputQueue.add(operatorStack.pop());
                }
                if (operatorStack.isEmpty() || !operatorStack.peek().equals("(")) {
                    throw new InvalidExpressionException("Mismatched parentheses.");
                }
                operatorStack.pop(); // Pop the left parenthesis '('
                if (!operatorStack.isEmpty() && isFunction(operatorStack.peek())) {
                    outputQueue.add(operatorStack.pop());
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            String op = operatorStack.pop();
            if (op.equals("(") || op.equals(")")) {
                throw new InvalidExpressionException("Mismatched parentheses.");
            }
            outputQueue.add(op);
        }

        return outputQueue;
    }

    /**
     * Step 2 (Part B): Evaluates a postfix (RPN) expression.
     * @param postfixTokens The list of tokens in postfix order.
     * @return The final calculated result.
     */
    private double evaluatePostfix(List<String> postfixTokens) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfixTokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new InvalidExpressionException("Invalid expression: insufficient operands for operator " + token);
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        if (b == 0) {
                            throw new ArithmeticException("Division by zero.");
                        }
                        stack.push(a / b);
                        break;
                }
            } else if (isFunction(token)) { // Handles Step 3
                if (stack.size() < 1) {
                    throw new InvalidExpressionException("Invalid expression: insufficient operands for function " + token);
                }
                double a = stack.pop();
                switch (token) {
                    case "sin":
                        stack.push(Math.sin(Math.toRadians(a)));
                        break;
                    case "cos":
                        stack.push(Math.cos(Math.toRadians(a)));
                        break;
                    case "tan":
                        stack.push(Math.tan(Math.toRadians(a)));
                        break;
                }
            }
        }
        if (stack.size() != 1) {
            throw new InvalidExpressionException("The expression is invalid.");
        }
        return stack.pop();
    }

    // Helper methods
    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return PRECEDENCE.containsKey(token) && !isFunction(token);
    }

    private boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("tan");
    }

}
