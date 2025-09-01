package com.github.igocoelho;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FailedAssertionsDemo {
    private final Calculator calculator = new Calculator();

    @Test
    void failsDueToUncaughtException() {
        // The following throws an ArithmeticException due to division by
        // zero, which causes a test failure.
//        calculator.divide(1, 0); // replaced by assertThrows!
        assertThrows(ArithmeticException.class, () -> calculator.divide(1,0));
    }

    @Test
    void testExpectedExceptionIsThrown() {
        // The following assertion succeeds because the code under assertion
        // throws the expected IllegalArgumentException.
        // The assertion also returns the thrown exception which can be used for
        // further assertions like asserting the exception message.
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> {
                    throw new IllegalArgumentException("expected message");
                });
        assertEquals("expected message", exception.getMessage());

        // The following assertion also succeeds because the code under assertion
        // throws IllegalArgumentException which is a subclass of RuntimeException.
        assertThrows(RuntimeException.class, () -> {
            throw new IllegalArgumentException("expected message");
        });
    }

    @Test
    @Disabled("Disabled until bug #99 has been fixed")
    void testWillBeSkipped() {
    }
}
