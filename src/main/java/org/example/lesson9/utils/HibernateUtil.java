package org.example.lesson9.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public final class HibernateUtil {
    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("lesson9");

    private HibernateUtil() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void close() {
        FACTORY.close();
    }

    public static boolean checkState() {
        return FACTORY.isOpen();
    }
}
