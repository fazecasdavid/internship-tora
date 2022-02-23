package com.tora.calculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CalculatorTest {
    public static final BigDecimal VALID_BIG_DECIMAL1 = new BigDecimal("10");
    public static final BigDecimal VALID_BIG_DECIMAL2 = new BigDecimal("20");
    public static final BigDecimal EXPECTED_SUM_OF_VALID_BIG_DECIMAL_NUMBERS = new BigDecimal("30");
    public static final BigDecimal EXPECTED_SUBTRACTION_OF_VALID_BIG_DECIMAL_NUMBERS = new BigDecimal("-30");
    public static final BigDecimal EXPECTED_MULTIPLICATION_OF_VALID_BIG_DECIMAL_NUMBERS = new BigDecimal("200");
    public static final BigDecimal EXPECTED_DIVISION_OF_VALID_BIG_DECIMAL_NUMBERS = new BigDecimal("0.005");
    public static final BigDecimal UNEXPECTED_SUM_OF_VALID_BIG_DECIMAL_NUMBERS = new BigDecimal("20");

    private Calculator calculator;

    @Before
    public void setUp() {
        calculator = new Calculator(BigDecimal.ZERO);
    }

    @After
    public void tearDown() {
        calculator = null;
    }

    @Test
    public void getResult() {
        assertEquals(0, calculator.getResult().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void addValidNumbers() {
        calculator.add(VALID_BIG_DECIMAL1);
        calculator.add(VALID_BIG_DECIMAL2);

        assertEquals(0, calculator.getResult().compareTo(EXPECTED_SUM_OF_VALID_BIG_DECIMAL_NUMBERS));
        assertNotEquals(0, calculator.getResult().compareTo(UNEXPECTED_SUM_OF_VALID_BIG_DECIMAL_NUMBERS));
    }

    @Test
    public void subtractValidNumbers() {
        calculator.subtract(VALID_BIG_DECIMAL1);
        calculator.subtract(VALID_BIG_DECIMAL2);

        assertEquals(0, calculator.getResult().compareTo(EXPECTED_SUBTRACTION_OF_VALID_BIG_DECIMAL_NUMBERS));
    }

    @Test
    public void multiplyValidNumbers() {
        calculator = new Calculator(BigDecimal.ONE);
        calculator.multiply(VALID_BIG_DECIMAL1);
        calculator.multiply(VALID_BIG_DECIMAL2);

        assertEquals(0, calculator.getResult().compareTo(EXPECTED_MULTIPLICATION_OF_VALID_BIG_DECIMAL_NUMBERS));
    }

    @Test
    public void divisionValidNumbers() {
        calculator = new Calculator(BigDecimal.ONE);
        calculator.divide(VALID_BIG_DECIMAL1);
        calculator.divide(VALID_BIG_DECIMAL2);

        assertEquals(0, calculator.getResult().compareTo(EXPECTED_DIVISION_OF_VALID_BIG_DECIMAL_NUMBERS));
    }

    @Test
    public void divisionWithZero() {
        calculator = new Calculator(BigDecimal.ONE);
        assertThrows(ArithmeticException.class, () -> calculator.divide(BigDecimal.ZERO));
    }
}
