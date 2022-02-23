package com.tora.calculator.operation.binary.impl;

import com.tora.calculator.operation.binary.BinaryOperation;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;

@ToString(callSuper = true)
public class DivisionOperation extends BinaryOperation<BigDecimal> {

    public DivisionOperation(BigDecimal leftOperand, BigDecimal rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public BigDecimal evaluate() {
        return leftOperand.divide(rightOperand, 10, RoundingMode.UP);
    }
}
