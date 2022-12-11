package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("When running MathUtils")
class MathUtilsTest {

    MathUtils mathUtils;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    static void beforeAllInit() {
        System.out.println("This needs to run before all");
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        mathUtils = new MathUtils();
        testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with tags " +  testInfo.getTags());
    }

    @AfterEach
    void cleanup() {
        System.out.println("Cleaning up....");
    }

    @Test
    @DisplayName("Testing add method")
    @Tag("Math")
    void testAdd() {
        int expected = 2;
        int actual = mathUtils.add(1, 1);
        assertEquals(expected, actual, "The add method should add two numbers");
    }

    @Nested
    @DisplayName("add method")
    @Tag("Math")
    class AddTest {
        @Test
        @DisplayName("when adding two positive numbers")
        void testAddPositive() {
            assertEquals(2, mathUtils.add(1, 1), "should return the right sum");
        }

        @Test
        @DisplayName("when adding two negative numbers")
        void testAddNegative() {
            int expected = -2;
            int actual = mathUtils.add(-1, -1);
            assertEquals(expected, actual, () -> "should return the right sum " + expected + " but returned " + actual);
        }
    }

    @RepeatedTest(3)
    @Tag("Circle")
    void testComputeCircleArea(RepetitionInfo repetitionInfo) {
        repetitionInfo.getCurrentRepetition();
        assertEquals(314.1592653589793, mathUtils.computeCircleArea(10), "Should return right circle area.");
    }

    @Test
    @DisplayName("multiply method")
    @Tag("Math")
    void testMultiply() {
        //assertEquals(4, mathUtils.multiply(2,2), "should return the right product");
        assertAll(
                () -> assertEquals(4, mathUtils.multiply(2, 2)),
                () -> assertEquals(0, mathUtils.multiply(2, 0)),
                () -> assertEquals(-2, mathUtils.multiply(2, -1))
        );
    }


    @Test
    @EnabledOnOs(OS.SOLARIS)
    @Tag("Math")
    void testDivide() {
        boolean isServerUp = false;
        assumeTrue(isServerUp);
        assertThrows(ArithmeticException.class, () -> mathUtils.divide(1, 0), "Divide by zero should throw.");
    }

    @Test
    @Disabled
    @DisplayName("TDD method. Should not run")
    void testDisabled() {
        fail("This test should be disabled.");
    }
}