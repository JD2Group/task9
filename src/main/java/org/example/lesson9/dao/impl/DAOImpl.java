package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.DAO;
import org.example.lesson9.utils.HibernateUtil;
import javax.persistence.EntityManager;
import java.io.Serializable;

public abstract class DAOImpl<T extends Serializable> implements DAO<T> {


    @Override
    public T save(T object) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        manager.persist(object);
        manager.getTransaction().commit();
        manager.close();
        return object;
    }

    @Override
    public T update(T object) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        manager.merge(object);
        manager.getTransaction().commit();
        manager.close();
        return object;
    }

    @Override
    public T get(int id) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        T result = manager.find(getDTOClass(), id);
        manager.getTransaction().commit();
        manager.close();
        return result;
    }

    @Override
    public void delete(int id) {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        T objectForDelete = manager.find(getDTOClass(), id);
        if (objectForDelete != null) {
            manager.remove(objectForDelete);
        }
        manager.getTransaction().commit();
        manager.close();
    }

    protected abstract Class<T> getDTOClass();
}
