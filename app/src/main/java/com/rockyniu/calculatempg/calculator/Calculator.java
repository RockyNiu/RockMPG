package com.rockyniu.calculatempg.calculator;

/**
 * Created by Lei on 2015/2/15.
 */
public class Calculator {
    public static float getQuotient(float dividend, float divisor) {
        if (!isInRange(dividend) || !isInRange(divisor)) {
            throw new IllegalArgumentException();
        }

        float quotient = dividend / divisor;

        if (quotient > Float.MAX_VALUE) {
            throw new IllegalArgumentException();
        }

        return quotient;
    }

    public static boolean isInRange(float value) {
        return (value > 0 && value <= Float.MAX_VALUE);
    }
}
