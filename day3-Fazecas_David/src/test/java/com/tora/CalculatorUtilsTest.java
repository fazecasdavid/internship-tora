package com.tora;

import org.junit.Test;

import static java.lang.Integer.MAX_VALUE;
import static com.tora.CalculatorUtils.add;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;


public class CalculatorUtilsTest {

    public static final int VALID_NUMBER1 = 200;
    public static final int VALID_NUMBER2 = 400;
    public static final int EXPECTED_SUM_OF_VALID_NUMBERS = 600;
    public static final int UNEXPECTED_SUM_OF_VALID_NUMBERS = 700;

    public static final int NEGATIVE_NUMBER = -200;
    public static final int OVERFLOWING_NUMBER = MAX_VALUE / 2 + 1;

    @Test
    public void addValidNumbers() {
        int actual = add(VALID_NUMBER1, VALID_NUMBER2);

        assertEquals(EXPECTED_SUM_OF_VALID_NUMBERS, actual);
        assertNotEquals(UNEXPECTED_SUM_OF_VALID_NUMBERS, actual);
    }
    @Test
    public void addNegativeNumbers() {
        assertThrows(ArithmeticException.class, () -> add(VALID_NUMBER1, NEGATIVE_NUMBER));
        assertThrows(ArithmeticException.class, () -> add(NEGATIVE_NUMBER, VALID_NUMBER1));
        assertThrows(ArithmeticException.class, () -> add(NEGATIVE_NUMBER, NEGATIVE_NUMBER));
    }

    @Test
    public void addOverflowingNumbers() {
        assertThrows(ArithmeticException.class, () -> add(OVERFLOWING_NUMBER, VALID_NUMBER1));
        assertThrows(ArithmeticException.class, () -> add(VALID_NUMBER1, OVERFLOWING_NUMBER));
        assertThrows(ArithmeticException.class, () -> add(OVERFLOWING_NUMBER, OVERFLOWING_NUMBER));
    }
}
