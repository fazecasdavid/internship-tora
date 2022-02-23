package com.tora.calculator.operation.unary.impl;

import com.tora.calculator.operation.unary.UnaryOperation;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.MathContext;

@ToString(callSuper = true)
public class SqrtOperation extends UnaryOperation<BigDecimal> {
    private final int precision;

    public SqrtOperation(BigDecimal operand) {
        super(operand);
        this.precision = 10;
    }

    public SqrtOperation(BigDecimal operand, int precision) {
        super(operand);
        this.precision = precision;
    }

    @Override
    public BigDecimal evaluate() {
        return operand.sqrt(new MathContext(precision));
    }
}
