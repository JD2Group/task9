package org.example.lesson9.utils;


import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;

import static org.junit.jupiter.api.Assertions.assertFalse;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(4)
class HibernateUtilTest {

    @Test
    public void closeTest() {
        HibernateUtil.close();

        assertFalse(HibernateUtil.checkState());
    }

}