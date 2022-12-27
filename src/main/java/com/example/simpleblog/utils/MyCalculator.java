/* (C)2022 */
package com.example.simpleblog.utils;

import java.util.List;

public class MyCalculator {

    private final String[] arrayOfAlphabets = {"A", "B", "C"};

    public int add(int a, int b) {
        return a + b;
    }

    public String[] getArrayOfString() {
        return arrayOfAlphabets;
    }

    public List<String> getListOfString() {
        return List.of("apple", "orange", "pear");
    }

    public void throwsAnException(boolean shouldThrow) throws Exception {
        if (shouldThrow) {
            throw new Exception("This is an exception.");
        }
    }
}
