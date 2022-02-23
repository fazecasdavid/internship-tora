package com.tora.calculator.operation.binary;

import com.tora.calculator.operation.Operation;
import lombok.ToString;

@ToString
public abstract class BinaryOperation<R> implements Operation<R> {
    protected final R leftOperand;
    protected final R rightOperand;


    protected BinaryOperation(R leftOperand, R rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }
}
