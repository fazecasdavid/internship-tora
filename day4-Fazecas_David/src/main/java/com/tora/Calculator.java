package com.tora;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    private BigDecimal result;

    public Calculator(BigDecimal result) {
        this.result = result;
    }

    public Calculator() {
        this.result = BigDecimal.ZERO;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void add(BigDecimal other) {
        result = result.add(other);
    }

    public void subtract(BigDecimal other) {
        result = result.subtract(other);
    }

    public void multiply(BigDecimal other) {
        result = result.multiply(other);
    }

    public void divide(BigDecimal other) {
        if (other.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        result = result.divide(other, 20, RoundingMode.UP);
    }
}
