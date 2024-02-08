package org.example.lesson9.utils.wrappers;


import java.util.function.Function;

public final class ThrowingFunctionWrapper {
    private ThrowingFunctionWrapper() {
    }

    public static <T, R, E extends Exception> Function<T, R> apply(ThrowingFunction<T, R, E> function, Class<E> exceptionClass) {
        return object -> {
            R result = null;
            try {
                result = function.apply(object);
            } catch (Exception e) {
                try {
                    E exception = exceptionClass.cast(e);
                    exception.printStackTrace();
                } catch (ClassCastException castException) {
                    throw new RuntimeException();
                }
            }
            return result;
        };
    }
}