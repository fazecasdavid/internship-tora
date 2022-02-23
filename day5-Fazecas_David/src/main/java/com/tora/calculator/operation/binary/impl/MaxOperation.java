package com.tora.calculator.operation.binary.impl;

import com.tora.calculator.operation.binary.BinaryOperation;
import lombok.ToString;

import java.math.BigDecimal;

@ToString(callSuper = true)
public class MaxOperation extends BinaryOperation<BigDecimal> {

    public MaxOperation(BigDecimal leftOperand, BigDecimal rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public BigDecimal evaluate() {
        return leftOperand.max(rightOperand);
    }
}
