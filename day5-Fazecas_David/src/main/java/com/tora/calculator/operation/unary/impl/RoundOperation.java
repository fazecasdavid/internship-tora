package com.tora.calculator.operation.unary.impl;

import com.tora.calculator.operation.unary.UnaryOperation;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ToString(callSuper = true)
public class RoundOperation extends UnaryOperation<BigDecimal> {
    private final int scale;

    public RoundOperation(BigDecimal operand) {
        super(operand);
        this.scale = 0;
    }

    public RoundOperation(BigDecimal operand, int scale) {
        super(operand);
        this.scale = scale;
    }


    @Override
    public BigDecimal evaluate() {
        return operand.setScale(scale, RoundingMode.UP);
    }
}
