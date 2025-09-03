package com.github.igocoelho;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.NamedExecutable;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static com.github.igocoelho.MyStringUtils.isPalindrome;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

public class DynamicTestsNamedDemo {

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStreamFactoryMethodWithNames() {
        // Stream of palindromes to check
        var inputStream = Stream.of(
            named("racecar is a palindrome", "racecar"),
            named("radar is also a palindrome", "radar"),
            named("mom also seems to be a palindrome", "mom"),
            named("dad is yet another palindrome", "dad")
        );

        // Returns a stream of dynamic tests.
        return DynamicTest.stream(inputStream, text -> assertTrue(isPalindrome(text)));
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStreamFactoryMethodWithNamedExecutables() {
        // Stream of palindromes to check
        var inputStream = Stream.of("racecar", "radar", "mom", "dad")
                .map(PalindromeNamedExecutable::new);

        // Returns a stream of dynamic tests based on NamedExecutables.
        return DynamicTest.stream(inputStream);
    }

    record PalindromeNamedExecutable(String text) implements NamedExecutable {

        @Override
        public String getName() {
            return String.format("'%s' is a palindrome", text);
        }

        @Override
        public void execute() {
            assertTrue(isPalindrome(text));
        }
    }
}