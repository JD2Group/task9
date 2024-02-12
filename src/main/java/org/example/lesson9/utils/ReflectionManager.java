package org.example.lesson9.utils;

import org.example.lesson9.utils.wrappers.ThrowingFunctionWrapper;

import javax.persistence.Id;
import java.util.Arrays;

public final class ReflectionManager {
    private ReflectionManager() {
    }

    public static <T> Object getId(T object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .peek(f -> f.setAccessible(true))
                .map(f -> ThrowingFunctionWrapper.apply(q -> f.get(object)).apply(object))
                .findFirst()
                .orElse(null);
    }
}
