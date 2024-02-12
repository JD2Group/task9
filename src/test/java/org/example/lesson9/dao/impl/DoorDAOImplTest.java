package org.example.lesson9.dao.impl;


import org.example.lesson9.dao.DoorDAO;
import org.example.lesson9.dto.DoorDTO;
import org.example.lesson9.utils.HibernateUtil;
import org.example.lesson9.utils_src.MockUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.example.lesson9.utils_src.MockConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(0)
class DoorDAOImplTest {
    private final DoorDAO doorDAO = new DoorDAOImpl();
    private final List<DoorDTO> doorDTOS = new ArrayList<>();

    @BeforeEach
    public void createDoorList() {
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(1), DOORS_TYPE.get(1)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(2), DOORS_TYPE.get(2)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(3), DOORS_TYPE.get(3)));
        doorDTOS.add(MockUtils.buildDoor(DOORS_SIZE.get(4), DOORS_TYPE.get(4)));
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void saveTest(DoorDTO doorDTO) {
        if (doorDTO != null) {
            int expectedId = 0;
            doorDAO.save(doorDTO);
            int actualId = doorDTO.getId();
            assertNotEquals(expectedId, actualId);

        } else {
            assertThrows(IllegalArgumentException.class, () -> doorDAO.save(doorDTO));
        }
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void deleteTest(DoorDTO doorDTO) {
        if (doorDTO != null) {
            int idBeforeSave = doorDTO.getId();
            doorDAO.delete(idBeforeSave);
            boolean isManagerClosed = HibernateUtil.getEntityManager().isOpen();
            assertTrue(isManagerClosed);

            doorDAO.save(doorDTO);
            int id = doorDTO.getId();
            doorDAO.delete(id);
            DoorDTO result = doorDAO.get(id);
            assertNull(result, "Expected: null" + ", actual: " + result);
        }
    }

    @ParameterizedTest()
    @MethodSource("cases")
    public void updateTest(DoorDTO dto) {
        if (dto != null) {
            String expected = dto.getType();
            doorDAO.save(dto);
            dto.setType("another");
            doorDAO.update(dto);
            String actual = doorDAO.get(dto.getId()).getType();

            assertNotEquals(expected, actual, "Expected type: " + expected + ", actual: " + actual);
        } else {
            assertThrows(IllegalArgumentException.class, () -> doorDAO.update(dto));
        }
    }

    @Test
    public void getBySizeTest() {
        doorDTOS.stream()
                .limit(3)
                .peek(d -> d.setSize(3500D))
                .forEach(doorDAO::save);

        List<DoorDTO> doorDTOList = doorDAO.getBySize(FROM_SIZE, TO_SIZE);
        int expected = 3;
        assertNotNull(doorDTOList);
        assertEquals(expected, doorDTOList.size(), "Expected: " + expected + ", actual: " + doorDTOList.size());

        List<DoorDTO> emptyList = doorDAO.getBySize(NULL_SIZE, NULL_SIZE);
        assertTrue(emptyList.isEmpty());
    }

    @AfterEach
    public void deleteAllDoors() {
        EntityManager manager = HibernateUtil.getEntityManager();
        manager.getTransaction().begin();
        Query deleteAll = manager.createNativeQuery(DELETE_ALL_DOORS_QUERY);
        deleteAll.executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    static Stream<Arguments> cases() {
        return Stream.of(

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(0), DOORS_TYPE.get(0))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(1), DOORS_TYPE.get(1))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(2), DOORS_TYPE.get(2))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(3), DOORS_TYPE.get(3))),

                Arguments.of(MockUtils.buildDoor(DOORS_SIZE.get(4), DOORS_TYPE.get(4))),

                Arguments.of((Object) null)

        );
    }

}