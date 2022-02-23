package com.tora.calculator;

import com.tora.calculator.history.CalculatorStateMemento;
import com.tora.calculator.operation.BinaryOperation;
import com.tora.calculator.operation.Operation;
import com.tora.calculator.operation.UnaryOperation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

@Data
public class Calculator {

    private BigDecimal result;

    public Calculator() {
        this.result = BigDecimal.ZERO;
    }

    public void applyBinaryOperator(BigDecimal other, BinaryOperator<BigDecimal> operator) {
        Operation<BigDecimal> operation = new BinaryOperation<>(result, other, operator);
        result = operation.evaluate();
    }

    public void applyUnaryOperator(UnaryOperator<BigDecimal> operator) {
        Operation<BigDecimal> operation = new UnaryOperation<>(result, operator);
        result = operation.evaluate();
    }

    // For undo operation: Memento design pattern
    public CalculatorStateMemento createMemento() {
        return new CalculatorStateMemento(result);
    }

    public void restoreFromMemento(CalculatorStateMemento save) {
        result = save.getResult();
    }
}
