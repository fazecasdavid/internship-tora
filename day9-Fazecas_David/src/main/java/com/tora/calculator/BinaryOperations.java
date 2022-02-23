package com.tora.calculator;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;


@Getter
public enum BinaryOperations {
    ADD("+", BigDecimal::add),
    SUBTRACT("-", BigDecimal::subtract),
    DIVIDE("/", (left, right) -> left.divide(right, 10, RoundingMode.UP)),
    MULTIPLY("*", BigDecimal::multiply),
    MAX("max", BigDecimal::max),
    MIN("min", BigDecimal::min),
    POW("pow", (left, right) -> left.pow(right.intValue()));

    private final String operator;
    private final BinaryOperator<BigDecimal> binaryOperator;

    BinaryOperations(String operator, BinaryOperator<BigDecimal> binaryOperator) {
        this.operator = operator;
        this.binaryOperator = binaryOperator;
    }

    public static Stream<BinaryOperations> stream() {
        return Stream.of(BinaryOperations.values());
    }
}
