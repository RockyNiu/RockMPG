package com.rockyniu.calculatempg.util;

/**
 * Created by Lei on 2015/2/16.
 */
public class StringHelper {
    public static String formatFloat(float value) {
        return String.format("%.3f", value);
    }

    public static String formatFloatWithLabel(String label, float value) {
        return String.format("%s %.3f\t", label, value);
    }
}
