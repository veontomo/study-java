package com.veontomo;

/**
 * This example shows that the wrapper classes cache some values
 */
public class Autoboxing {
    public static void main(String[] args) {

        final int[] numbers = new int[]{-129, -128, -127, 0, 1, 127, 128, 129};
        for (int number : numbers) {
            final Integer n = number;
            final Integer m = number;
            System.out.println("" + number + " is cached: " + (n == m));
        }

        final Boolean b1 = true;
        final Boolean b2 = false;

        System.out.println("" + b1 + " == " + b2 + "? " + (b1 == b2));

        final String s1 = "1234567890";
        final String s2 = "12345".toUpperCase() + "67890";

        System.out.println("'" + s1 + "' == '" + s2 + "'? " + (s1 == s2));
        System.out.println("'" + s1 + "' == '" + s2 + "'? " + (s1.intern() == s2.intern()));
    }
}
