package com.spring.ecomerce.utils;

public class NumberUtils {
    public static double myRound(double rate) {
        return (double) Math.round(rate * 100.0) / 100;
    }
}
