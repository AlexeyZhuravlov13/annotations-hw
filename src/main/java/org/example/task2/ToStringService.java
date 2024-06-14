package org.example.task2;

import org.example.task2.annotation.ToString;

import java.lang.reflect.Field;

public class ToStringService {

    public static final String DELIMITER = ", ";

    public String toString(Object o) throws IllegalAccessException {
        Class<?> metadata = o.getClass();
        Field[] declaredFields = metadata.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(metadata.getSimpleName()).append("{");
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            declaredField.setAccessible(true);
            if (declaredField.isAnnotationPresent(ToString.class)) {
                sb.append(declaredField.getName()).append("=").append(declaredField.get(o).toString());
                if (i != declaredFields.length - 1) {
                    sb.append(DELIMITER);
                }
            }
        }
        int endOfStringDelimiterIndex = sb.length() - 2;
        if (sb.lastIndexOf(DELIMITER) == endOfStringDelimiterIndex) {
            sb.replace(endOfStringDelimiterIndex, sb.length(), "");
        }
        sb.append("}");
        return sb.toString();
    }
}
