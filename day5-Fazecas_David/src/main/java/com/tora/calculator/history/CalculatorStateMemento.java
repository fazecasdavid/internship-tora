package com.tora.calculator.history;

import java.math.BigDecimal;

public class CalculatorStateMemento {
    private final BigDecimal result;

    public CalculatorStateMemento(BigDecimal result) {
        this.result = result;
    }

    public BigDecimal getResult() {
        return result;
    }
}
