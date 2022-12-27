/* (C)2022 */
package com.example.simpleblog.utils;

import static java.util.Arrays.asList;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.*;

// @DisplayNameGeneration(DisplayNameGenerator.Simple.class)
// @DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class) // test class name and
// method name
// @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
// @DisplayNameGeneration(DisplayNameGenerator.Standard.class)
class MyCalculatorTest {

    MyCalculator myCalculator;

    @BeforeAll
    static void beforeAllSetup() {
        /*
        - get database connections
        - connect to remote servers
        - setup test data in db
         */
        System.out.println("@BeforeAll lifecycle\n");
    }

    @AfterAll
    static void afterAllTearDown() {
        /*
        - release database connections
        - disconnect remote servers
        - cleanup test data in db
         */
        System.out.println("@AfterAll lifecycle\n");
    }

    @BeforeEach
    void setup() {
        System.out.println("@BeforeEach setup");
        myCalculator = new MyCalculator();
    }

    @AfterEach
    void tearDown() {
        System.out.println("@AfterEach tearDown\n");
    }

    @Test
    @DisplayName("should return 3 when adding 1 and 2")
    void testAddPositiveValues() {
        int expected = 3;

        int result = myCalculator.add(1, 2);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("should return -1 when adding 1 and -2")
    void testAddPositiveAndNegativeValues() {
        int expected = -1;

        int result = myCalculator.add(1, -2);

        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("assertArrayEquals - order matters")
    void testAssertArrayEquals() {
        String[] expected = {"A", "B", "C"};

        String[] result = myCalculator.getArrayOfString();

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName(
            "assertIterableEquals - in order to be equal, both iterable must return equal elements"
                + " in the same order and it isn't required that the two iterables are of the same"
                + " type in order to be equal.")
    void testAssertIterableEquals() {
        LinkedList<String> expected = new LinkedList<>(asList("apple", "orange", "pear"));

        List<String> result = myCalculator.getListOfString();

        Assertions.assertIterableEquals(expected, result);
    }

    @Test
    @DisplayName("assertLinesMatch - order matters")
    /*
    This method differs from the assertEquals and assertIterableEquals since, for each pair of expected and actual lines, it performs this algorithm:
        1. check if the expected line is equal to the actual one. If yes it continues with the next pair
        2. treat the expected line as a regular expression and performs a check with the String.matches() method. If yes it continues with the next pair
        3. check if the expected line is a fast-forward marker. If yes apply fast-forward and repeat the algorithm from the step 1
     */
    void testAssertLinesMatch() {
        List<String> expected = List.of("apple", "orange", "pear");

        List<String> result = myCalculator.getListOfString();

        Assertions.assertLinesMatch(expected, result);
    }

    @Test
    @DisplayName("assertThrows")
    void testAssertThrows() {
        Assertions.assertThrows(
                Exception.class,
                () -> myCalculator.throwsAnException(true),
                "Should throw an exception");
    }

    @Test
    @DisplayName("assertThrows with custom exception message")
    void testAssertThrowsWithCustomExceptionMessage() {
        String expectedExceptionMessage = "This is an exception.";

        Exception exception =
                Assertions.assertThrows(
                        Exception.class,
                        () -> myCalculator.throwsAnException(true),
                        "Should throw an exception");

        Assertions.assertEquals(expectedExceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("assertDoesNotThrow")
    void testAssertDoesNotThrow() {
        Assertions.assertDoesNotThrow(
                () -> myCalculator.throwsAnException(false), "Should not throw exception");
    }
}
