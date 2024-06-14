package org.example.task2;

import org.example.task2.annotation.ToString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToStringServiceTest {
    ToStringService toStringService = new ToStringService();

    @Test
    void testToString() throws IllegalAccessException {
        TestUser testUserToValidate = new TestUser(1, 50, "Alex", "AA000000", "QWERTY");
        String expected = "TestUser{age=50, name=Alex, passport=AA000000}";
        String actual = toStringService.toString(testUserToValidate);
        assertEquals(expected, actual);
    }

    private static class TestUser {
        public TestUser(int id, int age, String name, String passport, String password) {
            this.id = id;
            this.age = age;
            this.name = name;
            this.passport = passport;
            this.password = password;
        }

        private int id;

        @ToString
        private int age;
        @ToString
        private String name;
        @ToString
        private String passport;

        private String password;
    }
}