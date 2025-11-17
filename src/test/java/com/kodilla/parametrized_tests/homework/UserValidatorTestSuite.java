package com.kodilla.parametrized_tests.homework;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTestSuite {

    private UserValidator validator = new UserValidator();

    @ParameterizedTest
    @ValueSource(strings = {"abc", "user.name", "user_123", "a-b_c", "___", "a1."})
    public void shouldVerifyCorrectUserNames(String username) {
        assertTrue(validator.validateUsername(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ab", "@user", "user!"})
    public void shouldVerifyIncorrectUserNames(String username) {
        assertFalse(validator.validateUsername(username));
    }

    @ParameterizedTest
    @EmptySource
    public void shouldReturnFalseIfUsernameIsEmpty(String username) {
        assertFalse(validator.validateUsername(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"john.doe@example.com", "user123@example.co.uk", "user-name_123@my-domain.info", "a@b.pl", "name@domain.c", "a@b.cdefgh"})
    public void shouldVerifyCorrectEmail(String email) {
        assertTrue(validator.validateEmail(email));
    }

    @ParameterizedTest
    @ValueSource(strings = {"john..doe@example.com", ".john@example.com", "john@.example.com", "john@example", "john@example.abcdefg", "john@exa$mple.com", "john@ex ample.com", "@example.com", "john@example..com"})
    public void shouldVerifyIncorrectEmail(String email) {
        assertFalse(validator.validateEmail(email));
    }

    @ParameterizedTest
    @EmptySource
    public void shouldReturnFalseIfEmailIsEmpty(String email) {
        assertFalse(validator.validateEmail(email));
    }
}