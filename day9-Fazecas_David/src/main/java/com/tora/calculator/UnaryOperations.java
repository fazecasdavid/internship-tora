package com.tora.calculator;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Getter
public enum UnaryOperations {
    ROUND("round", operand -> operand.setScale(0, RoundingMode.UP)),
    SQRT("sqrt", operand -> operand.sqrt(new MathContext(10)));

    private final String operator;
    private final UnaryOperator<BigDecimal> unaryOperator;

    UnaryOperations(String operator, UnaryOperator<BigDecimal> unaryOperator) {
        this.operator = operator;
        this.unaryOperator = unaryOperator;
    }

    public static Stream<UnaryOperations> stream() {
        return Stream.of(UnaryOperations.values());
    }
}
