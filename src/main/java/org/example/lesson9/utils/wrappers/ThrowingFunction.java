package org.example.lesson9.utils.wrappers;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {
    R apply(T object) throws E;
}
