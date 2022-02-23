package com.tora;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;

public class CalculatorUtils {

    /**
     * @param x - positive and less than Integer.MAX_VALUE / 2
     * @param y - positive and less than Integer.MAX_VALUE / 2
     * @return - the addition of x and y
     * @throws ArithmeticException if the numbers are negative or greater than Integer.MAX_VALUE / 2
     */
    public static int add(int x, int y) {
        if (x < 0 || y < 0)
            throw new ArithmeticException("The numbers must be positive");

        if (x > MAX_VALUE / 2 || y > MAX_VALUE / 2)
            throw new ArithmeticException(format("The numbers must not exceed %d", MAX_VALUE / 2));

        return x + y;
    }
}
