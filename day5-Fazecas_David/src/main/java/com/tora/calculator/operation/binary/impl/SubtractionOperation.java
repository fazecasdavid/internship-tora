package com.tora.calculator.operation.binary.impl;

import com.tora.calculator.operation.binary.BinaryOperation;
import lombok.ToString;

import java.math.BigDecimal;

@ToString(callSuper = true)
public class SubtractionOperation extends BinaryOperation<BigDecimal> {
    public SubtractionOperation(BigDecimal leftOperand, BigDecimal rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public BigDecimal evaluate() {
        return leftOperand.subtract(rightOperand);
    }
}
