package org.example.lesson9.dao;

import java.io.Serializable;

public interface DAO<T extends Serializable> {
    T save(T object);

    T update(T object);

    T get(int id);

    void delete(int id);
}
