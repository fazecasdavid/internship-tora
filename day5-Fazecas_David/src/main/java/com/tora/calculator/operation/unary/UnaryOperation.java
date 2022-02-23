package com.tora.calculator.operation.unary;

import com.tora.calculator.operation.Operation;
import lombok.ToString;

@ToString
public abstract class UnaryOperation<R> implements Operation<R> {
    protected final R operand;

    protected UnaryOperation(R operand) {
        this.operand = operand;
    }
}
