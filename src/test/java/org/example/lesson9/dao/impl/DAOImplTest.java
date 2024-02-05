package org.example.lesson9.dao.impl;

import org.example.lesson9.dao.DAO;
import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.dto.HouseDTO;
import org.example.lesson9.utils.HibernateUtil;
import org.example.lesson9.utils.ReflectionManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;
import java.util.stream.Stream;

import static org.example.lesson9.dao.impl.MockConstants.*;
import static org.junit.jupiter.api.Assertions.*;

class DAOImplTest<T extends Serializable> {
    private final DAO<T> DAO = new DAOImpl<>();

    @ParameterizedTest()
    @MethodSource("cases")
    public void saveTest(T object, Class<T> clazz) {
        if (object != null) {
            int expectedId = 0;
            DAO.save(object, clazz);
            int actualId = (int) ReflectionManager.getId(object);
            assertNotEquals(expectedId, actualId);

            int expectedIdAfterMerge = (int) ReflectionManager.getId(object);
            DAO.save(object, clazz);
            int actualIdAfterMerge = (int) ReflectionManager.getId(object);
            assertEquals(expectedIdAfterMerge, actualIdAfterMerge);
        } else {
            assertThrows(IllegalArgumentException.class, () -> DAO.save(object, clazz));
        }
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void deleteTest(T dto, Class<T> clazz) {
        if (dto != null) {
            int idBeforeSave = (int) ReflectionManager.getId(dto);
            DAO.delete(idBeforeSave, clazz);
            boolean isManagerClosed = HibernateUtil.getEntityManager().isOpen();
            assertTrue(isManagerClosed);

            DAO.save(dto, clazz);
            int id = (int) ReflectionManager.getId(dto);
            DAO.delete(id, clazz);
            T result = DAO.get(id, clazz);
            assertNull(result, "Expected: null" + ", actual: " + result);
        }
    }

    static Stream<Arguments> cases() {
        return Stream.of(

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0)), DoorDTO.class),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(1), DOORS_TYPE.get(1)), DoorDTO.class),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(2), DOORS_TYPE.get(2)), DoorDTO.class),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(3), DOORS_TYPE.get(3)), DoorDTO.class),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(4), DOORS_TYPE.get(4)), DoorDTO.class),

                Arguments.of(null, DoorDTO.class),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(0), HOUSES_COLOR.get(0), HOUSES_ROOM.get(0)), HouseDTO.class),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(1), HOUSES_COLOR.get(1), HOUSES_ROOM.get(1)), HouseDTO.class),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(2), HOUSES_COLOR.get(2), HOUSES_ROOM.get(2)), HouseDTO.class),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(3), HOUSES_COLOR.get(3), HOUSES_ROOM.get(3)), HouseDTO.class),

                Arguments.of(MockUtils.buildHouse(HOUSES_SIZE.get(4), HOUSES_COLOR.get(4), HOUSES_ROOM.get(4)), HouseDTO.class),

                Arguments.of(null, HouseDTO.class)
        );
    }

}