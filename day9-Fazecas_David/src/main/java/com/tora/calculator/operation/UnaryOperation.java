package com.tora.calculator.operation;

import lombok.Data;

import java.util.function.UnaryOperator;

@Data
public class UnaryOperation<R> implements Operation<R> {
    private final R operand;
    private final UnaryOperator<R> unaryOperator;

    @Override
    public R evaluate() {
        return unaryOperator.apply(operand);
    }
}
