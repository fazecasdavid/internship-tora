package com.tora.calculator.operation;

import lombok.Data;

import java.util.function.BinaryOperator;

@Data
public class BinaryOperation<R> implements Operation<R> {
    private final R leftOperand;
    private final R rightOperand;
    private final BinaryOperator<R> binaryOperator;

    @Override
    public R evaluate() {
        return binaryOperator.apply(leftOperand, rightOperand);
    }
}
