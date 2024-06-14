package org.example.task1;

import org.example.task1.annotations.Max;
import org.example.task1.annotations.Min;
import org.example.task1.annotations.NotNull;
import org.example.task1.annotations.Regex;
import org.example.task1.exception.FieldValidationException;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {
    public void isValid(Object o) throws IllegalAccessException {
        Class<?> metadata = o.getClass();
        Field[] declaredFields = metadata.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            validateNotNull(o, field);
            validateMin(o, field);
            validateMax(o, field);
            validateRegexp(o, field);
        }
    }

    private void validateRegexp(Object o, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(Regex.class)) {
            Regex annotation = field.getAnnotation(Regex.class);
            String fieldValue = (String) field.get(o);
            String regex = annotation.value();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(fieldValue);
            if (!matcher.matches()) {
                throw new FieldValidationException("The field does not match the given regex pattern: " + regex);
            }
        }
    }


    private void validateMin(Object o, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(Min.class)) {
            Min min = field.getAnnotation(Min.class);
            int minValue = min.value();
            int fieldValue = (int) field.get(o);
            if (fieldValue < minValue) {
                throw new FieldValidationException("Field value should be greater than min value: " + minValue);
            }
        }

    }

    private void validateMax(Object o, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(Max.class)) {
            Max max = field.getAnnotation(Max.class);
            int maxValue = max.value();
            int fieldValue = (int) field.get(o);
            if (fieldValue > maxValue) {
                throw new FieldValidationException("Field value should be less than max value: " + maxValue);
            }
        }
    }

    private static void validateNotNull(Object o, Field field) throws IllegalAccessException {
        if (field.isAnnotationPresent(NotNull.class)) {
            Object fieldValue = field.get(o);
            if (Objects.isNull(fieldValue)) {
                throw new FieldValidationException("Field must be not null");
            }
        }
    }
}
