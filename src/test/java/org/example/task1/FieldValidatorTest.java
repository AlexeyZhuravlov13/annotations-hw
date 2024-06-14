package org.example.task1;

import org.example.task1.annotations.Max;
import org.example.task1.annotations.Min;
import org.example.task1.annotations.NotNull;
import org.example.task1.annotations.Regex;
import org.example.task1.exception.FieldValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldValidatorTest {

    private final FieldValidator fieldValidator = new FieldValidator();

    @Test
    void shouldNotThrowExceptionWhenValidMin() {
        TestUser testUserToValidate = new TestUser(1, "Oleksii", "AA000000");
        assertDoesNotThrow(() -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldThrowExceptionWhenInvalidMin() {
        TestUser testUserToValidate = new TestUser(-1, "Oleksii", "AA000000");
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldNotThrowExceptionWhenValidMax() {
        TestUser testUserToValidate = new TestUser(123, "Oleksii", "AA000000");
        assertDoesNotThrow(() -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldThrowExceptionWhenInvalidMax() {
        TestUser testUserToValidate = new TestUser(200, "Oleksii", "AA000000");
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldNotThrowExceptionWhenFieldIsNotNull() {
        TestUser testUserToValidate = new TestUser(50, "Alex", "AA000000");
        assertDoesNotThrow(() -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldThrowExceptionWhenFieldIsNull() {
        TestUser testUserToValidate = new TestUser(50, null, "AA000000");
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldNotThrowExceptionWhenFieldMatchRegex() {
        TestUser testUserToValidate = new TestUser(50, "Alex", "AA000000");
        assertDoesNotThrow(() -> fieldValidator.isValid(testUserToValidate));
    }

    @Test
    void shouldThrowExceptionWhenFieldIsNotMatchRegex() {
        TestUser testUserToValidate = new TestUser(50, "Alex", "A0A0000");
        assertThrows(FieldValidationException.class, () -> fieldValidator.isValid(testUserToValidate));
    }

    private static class TestUser {
        public TestUser(int age, String name, String passport) {
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