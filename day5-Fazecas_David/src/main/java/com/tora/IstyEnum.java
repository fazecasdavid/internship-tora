package com.tora;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;


enum BinaryOperations {
    ADD("+", BigDecimal::add),
    SUBTRACT("-", BigDecimal::subtract);
    // add new operations here
    private final String operator;
    private final BinaryOperator<BigDecimal> binaryOperator;

    private static final Map<String, BinaryOperations> operationsByOperator = Stream.of(values())
        .collect(toMap(operation -> operation.operator, Function.identity()));

    BinaryOperations(String operator, BinaryOperator<BigDecimal> binaryOperator) {
        this.operator = operator;
        this.binaryOperator = binaryOperator;
    }

    public static BinaryOperations getBinaryOperation(final String operator) {
        return operationsByOperator.get(operator);
    }

    BigDecimal evaluate(final BigDecimal left, final BigDecimal right) {
        return new BinaryOperation(left, right, binaryOperator).evaluate();
    }
}


interface Operation<T> {
    T evaluate();
}

class BinaryOperation implements Operation<BigDecimal> {
    private final BigDecimal leftOperand;
    private final BigDecimal rightOperand;
    private final BinaryOperator<BigDecimal> binaryOperator;


    public BinaryOperation(final BigDecimal leftOperand, final BigDecimal rightOperand, final BinaryOperator<BigDecimal> binaryOperator) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.binaryOperator = binaryOperator;
    }

    @Override
    public BigDecimal evaluate() {
        return binaryOperator.apply(leftOperand, rightOperand);
    }
}
