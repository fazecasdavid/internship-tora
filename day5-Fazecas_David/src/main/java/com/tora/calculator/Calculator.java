package com.tora.calculator;

import com.tora.calculator.history.CalculatorStateMemento;
import com.tora.calculator.operation.Operation;
import com.tora.calculator.operation.binary.impl.AdditionOperation;
import com.tora.calculator.operation.binary.impl.DivisionOperation;
import com.tora.calculator.operation.binary.impl.MaxOperation;
import com.tora.calculator.operation.binary.impl.MinOperation;
import com.tora.calculator.operation.binary.impl.MultiplicationOperation;
import com.tora.calculator.operation.binary.impl.PowerOfOperation;
import com.tora.calculator.operation.binary.impl.SubtractionOperation;
import com.tora.calculator.operation.unary.impl.RoundOperation;
import com.tora.calculator.operation.unary.impl.SqrtOperation;

import java.math.BigDecimal;

public class Calculator {

    private BigDecimal result;

    public Calculator(BigDecimal result) {
        this.result = result;
    }

    public Calculator() {
        this(BigDecimal.ZERO);
    }

    public BigDecimal getResult() {
        return result;
    }

    public void add(BigDecimal other) {
        Operation<BigDecimal> operation = new AdditionOperation(result, other);
        result = operation.evaluate();
    }

    public void subtract(BigDecimal other) {
        Operation<BigDecimal> operation = new SubtractionOperation(result, other);
        result = operation.evaluate();
    }

    public void multiply(BigDecimal other) {
        Operation<BigDecimal> operation = new MultiplicationOperation(result, other);
        result = operation.evaluate();
    }

    public void divide(BigDecimal other) {
        Operation<BigDecimal> operation = new DivisionOperation(result, other);
        result = operation.evaluate();
    }

    public void max(BigDecimal other) {
        Operation<BigDecimal> operation = new MaxOperation(result, other);
        result = operation.evaluate();
    }

    public void min(BigDecimal other) {
        Operation<BigDecimal> operation = new MinOperation(result, other);
        result = operation.evaluate();
    }

    public void powerOf(BigDecimal other) {
        Operation<BigDecimal> operation = new PowerOfOperation(result, other);
        result = operation.evaluate();
    }

    public void round() {
        Operation<BigDecimal> operation = new RoundOperation(result);
        result = operation.evaluate();
    }

    public void sqrt() {
        Operation<BigDecimal> operation = new SqrtOperation(result);
        result = operation.evaluate();
    }

    public CalculatorStateMemento createMemento() {
        return new CalculatorStateMemento(result);
    }

    public void restoreFromMemento(CalculatorStateMemento save) {
        result = save.getResult();
    }
}
