package org.example.lesson9.utils.wrappers;


import java.util.function.Function;

public final class ThrowingFunctionWrapper {
    private ThrowingFunctionWrapper() {
    }

    public static <T, R, E extends Exception> Function<T, R> apply(ThrowingFunction<T, R, E> function) {
        return object -> {
            R result;
            try {
                result = function.apply(object);
            } catch (Exception e) {
                throw new RuntimeException(e.getCause());
            }
            return result;
        };
    }
}