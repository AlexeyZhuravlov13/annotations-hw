package org.example.task1;

import org.example.task1.annotations.Max;
import org.example.task1.annotations.Min;
import org.example.task1.annotations.NotNull;
import org.example.task1.annotations.Regex;
import org.example.task1.exception.FieldValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidatorTest {

    private final FieldValidator fieldValidator = new FieldValidator();

    @Test
    void shouldNotThrowExceptionWhenValidMin() {
        User userToValidate = new User(1, "Oleksii", "AA000000" );
        assertDoesNotThrow(() ->fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldThrowExceptionWhenInvalidMin() {
        User userToValidate = new User(-1, "Oleksii", "AA000000" );
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldNotThrowExceptionWhenValidMax() {
        User userToValidate = new User(123, "Oleksii", "AA000000" );
        assertDoesNotThrow(() ->fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldThrowExceptionWhenInvalidMax() {
        User userToValidate = new User(200, "Oleksii", "AA000000" );
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldNotThrowExceptionWhenFieldIsNotNull() {
        User userToValidate = new User(50, "Alex", "AA000000" );
        assertDoesNotThrow(() ->fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldThrowExceptionWhenFieldIsNull() {
        User userToValidate = new User(50, null, "AA000000" );
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldNotThrowExceptionWhenFieldMatchRegex() {
        User userToValidate = new User(50, "Alex", "AA000000" );
        assertDoesNotThrow(() ->fieldValidator.isValid(userToValidate));
    }

    @Test
    void shouldThrowExceptionWhenFieldIsNotMatchRegex() {
        User userToValidate = new User(50, "Alex", "A0A0000" );
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(userToValidate));
    }


    private static class User{
        public User(int age, String name, String passport) {
            this.age = age;
            this.name = name;
            this.passport = passport;
        }

        @Min(1)
        @Max(123)
        @NotNull
        private int age;
        @NotNull
        private String name;
        @NotNull
        @Regex("^[A-Za-z]{2}\\d{6}$")
        private String passport;
    }
}