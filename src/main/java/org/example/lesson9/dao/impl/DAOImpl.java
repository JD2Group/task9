package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.DAO;
import org.example.lesson9.utils.HibernateUtil;
import org.example.lesson9.utils.ReflectionManager;
import org.example.lesson9.utils.jacoco.Generated;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static org.example.lesson9.utils.Constants.OBJECT_IS_NULL;

public class DAOImpl<T extends Serializable> implements DAO<T> {
    private EntityManager manager;

    @Override
    public T save(T object, Class<T> clazz) {
        if (object != null) {
            startTransaction();
            if (manager.find(clazz, ReflectionManager.getId(object)) == null) {
                manager.persist(object);
            } else {
                manager.merge(object);
            }
            commit();
        } else {
            throw new IllegalArgumentException(OBJECT_IS_NULL);
        }
        return object;
    }

    @Override
    public T update(T object) {
        if (object != null) {
            startTransaction();
            manager.merge(object);
            commit();
        } else {
            throw new IllegalArgumentException(OBJECT_IS_NULL);
        }
        return object;
    }

    @Override
    public T get(int id, Class<T> clazz) {
        startTransaction();
        T result = manager.find(clazz, id);
        commit();
        return result;
    }

    @Override
    public void delete(int id, Class<T> clazz) {
        startTransaction();
        T objectForDelete = manager.find(clazz, id);
        if (objectForDelete != null) {
            manager.remove(objectForDelete);
        }
        commit();
    }

    protected void startTransaction() {
        manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
    }

    @Generated
    protected void commit() {
        if (manager != null && manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
            manager.close();
        }
    }

    protected EntityManager getManager() {
        return manager;
    }
}
