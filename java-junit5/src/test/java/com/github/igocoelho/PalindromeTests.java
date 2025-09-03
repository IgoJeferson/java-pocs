package com.github.igocoelho;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.Parameter;
import org.junit.jupiter.params.ParameterizedClass;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ParameterizedClass
@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
class PalindromeTests {

    @Parameter
    String candidate;

    @Test
    void palindrome() {
        assertTrue(MyStringUtils.isPalindrome(candidate));
    }

    @Test
    void reversePalindrome() {
        String reverseCandidate = new StringBuilder(candidate).reverse().toString();
        assertTrue(MyStringUtils.isPalindrome(reverseCandidate));
    }

}