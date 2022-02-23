package com.tora.calculator.history;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CalculatorStateMemento {
    private final BigDecimal result;
}
